package org.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class test2 {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
//        System.out.println("调用登录程序");
        String username = UserAuth.login(scanner);
        if (username != null) {
            String permissionLevel = getPermissionLevel(username);
//            System.out.println("您的权限等级是：" + permissionLevel);

            // 提示用户输入想要访问的数据库
            System.out.println("请输入想要访问的数据库（A, B, C）:");
            String databaseChoice = scanner.nextLine().toUpperCase();

            // 使用权限等级调用test1中的方法
            String value = null;
            try {
                value = test1.getValueForKey(permissionLevel);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            System.out.println("Stored value for permission level '" + permissionLevel + "': " + value);
            // 判断value中是否包含用户选择的数据库
            if (value.contains(databaseChoice)) {
                System.out.println("访问成功");
            } else {
                System.out.println("权限不足");
            }
        }
    }

    private static String getPermissionLevel(String username) {
        synchronized (UserAuth.fileLock) {  // 假设fileLock是public的
            try (BufferedReader reader = new BufferedReader(new FileReader(UserAuth.PERMISSION_FILE_PATH))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(":");
                    if (parts[0].equals(username)) {
                        return parts[1]; // 返回权限等级
                    }
                }
            } catch (IOException e) {
                System.out.println("读取权限文件时出错：" + e.getMessage());
            }
            return "未知"; // 如果找不到用户或出现错误，返回未知
        }
    }
}
