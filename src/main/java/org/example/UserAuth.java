package org.example;

import java.io.*;
import java.security.*;
import java.util.Base64;
import java.util.Scanner;

public class UserAuth {

    private static final String USER_FILE_PATH = "users.txt";
    public static final String PERMISSION_FILE_PATH = "permissions.txt";
    private static final String KEY_FILE_PATH = "keys.txt";
    public static final Object fileLock = new Object();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("请选择操作：1. 注册  2. 登录  3. 退出");
            String choice = scanner.nextLine();
            switch (choice) {
                case "1":
                    register(scanner);
                    break;
                case "2":
                    login(scanner);
                    break;
                case "3":
                    System.out.println("退出程序");
                    return;
                default:
                    System.out.println("无效选择，请重试。");
            }
        }
    }

    private static void register(Scanner scanner) {
        System.out.println("请输入用户名：");
        String username = scanner.nextLine();
        System.out.println("请输入密码：");
        String password = scanner.nextLine();
        System.out.println("请输入权限等级（1, 2, 3）：");
        String permission = scanner.nextLine();
        String hashedPassword = hashPassword(password);

        synchronized (fileLock) {
            if (isUserExists(username)) {
                System.out.println("用户名已存在，请选择其他用户名。");
                return;
            }

            try (BufferedWriter userWriter = new BufferedWriter(new FileWriter(USER_FILE_PATH, true));
                 BufferedWriter permissionWriter = new BufferedWriter(new FileWriter(PERMISSION_FILE_PATH, true))) {
                userWriter.write(username + ":" + hashedPassword);
                userWriter.newLine();
                permissionWriter.write(username + ":" + permission);
                permissionWriter.newLine();
                System.out.println("注册成功！");
                generateAndStoreKeys(username);
            } catch (IOException e) {
                System.out.println("注册失败：" + e.getMessage());
            }
        }
    }

    public static String login(Scanner scanner) {
        System.out.println("请输入用户名：");
        String username = scanner.nextLine();
        System.out.println("请输入密码：");
        String password = scanner.nextLine();
        String hashedPassword = hashPassword(password);

        synchronized (fileLock) {
            if (authenticate(username, hashedPassword)) {
                System.out.println("登录成功！");
                return username; // 返回用户名
            } else {
                System.out.println("用户名或密码错误，请重试。");
                return null; // 登录失败返回null
            }
        }
    }

    private static boolean isUserExists(String username) {
        synchronized (fileLock) {
            try (BufferedReader reader = new BufferedReader(new FileReader(USER_FILE_PATH))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(":");
                    if (parts[0].equals(username)) {
                        return true;
                    }
                }
            } catch (IOException e) {
                System.out.println("检查用户时出错：" + e.getMessage());
            }
            return false;
        }
    }

    private static boolean authenticate(String username, String hashedPassword) {
        synchronized (fileLock) {
            try (BufferedReader reader = new BufferedReader(new FileReader(USER_FILE_PATH))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(":");
                    if (parts[0].equals(username) && parts[1].equals(hashedPassword)) {
                        return true;
                    }
                }
            } catch (IOException e) {
                System.out.println("验证用户时出错：" + e.getMessage());
            }
            return false;
        }
    }

    private static String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("哈希算法不可用： " + e.getMessage());
        }
    }

    private static void generateAndStoreKeys(String username) {
        try {
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
            keyGen.initialize(2048);
            KeyPair pair = keyGen.generateKeyPair();
            PrivateKey privateKey = pair.getPrivate();
            PublicKey publicKey = pair.getPublic();

            String encodedPrivateKey = Base64.getEncoder().encodeToString(privateKey.getEncoded());
            String encodedPublicKey = Base64.getEncoder().encodeToString(publicKey.getEncoded());

            synchronized (fileLock) {
                try (BufferedWriter keyWriter = new BufferedWriter(new FileWriter(KEY_FILE_PATH, true))) {
                    keyWriter.write(username + ":");
                    keyWriter.write("PublicKey=" + encodedPublicKey);
                    keyWriter.write(", PrivateKey=" + encodedPrivateKey);
                    keyWriter.newLine();
                }
            }

            System.out.println("公钥和私钥已生成并存储。");
        } catch (NoSuchAlgorithmException | IOException e) {
            System.out.println("生成密钥时出错：" + e.getMessage());
        }
    }
}
