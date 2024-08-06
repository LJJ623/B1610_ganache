package org.example;

import org.example.contracts.KeyValueStore;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;
import org.web3j.crypto.Credentials;
import org.web3j.tx.exceptions.ContractCallException;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.tx.gas.StaticGasProvider;

import java.math.BigInteger;

//public class test1 {
//    private static final String GANACHE_URL = "http://113.54.211.176:7545";
//    private static final String PRIVATE_KEY = "0x7ffc3861bc60b6133ff21f20d2046a18585a70197bc77caf369ec7359e112b91"; // 请替换为您的私钥
//    private static final String CONTRACT_ADDRESS = "0xF11C8fb332129B532C3B1b734141CA2A44c52503"; // 请替换为您的合约地址
//
//    private static final BigInteger GAS_PRICE = BigInteger.valueOf(20000000000L); // 20 Gwei
//    private static final BigInteger GAS_LIMIT = BigInteger.valueOf(6721975L); // 可以设置为Ganache的默认上限

//    public static void main(String[] args) throws Exception {
//        // 连接到Ganache
//        Web3j web3j = Web3j.build(new HttpService(GANACHE_URL));
//
//        // 加载凭证
//        Credentials credentials = Credentials.create(PRIVATE_KEY);
//
//        // 设置Gas Provider
//        ContractGasProvider gasProvider = new StaticGasProvider(GAS_PRICE, GAS_LIMIT);
//
//        // 加载合约
//        KeyValueStore contract = KeyValueStore.load(CONTRACT_ADDRESS, web3j, credentials, gasProvider.getGasPrice(), gasProvider.getGasLimit());
//
//        // 存储键值对 (1, "A")
//        TransactionReceipt receipt1 = contract.set("1", "A").send();
//        System.out.println("Transaction receipt for (1, 'A'): " + receipt1.getTransactionHash());
//
//        // 存储键值对 (2, "AB")
//        TransactionReceipt receipt2 = contract.set("2", "AB").send();
//        System.out.println("Transaction receipt for (2, 'AB'): " + receipt2.getTransactionHash());
//
//        // 存储键值对 (3, "ABC")
//        TransactionReceipt receipt3 = contract.set("3", "ABC").send();
//        System.out.println("Transaction receipt for (3, 'ABC'): " + receipt3.getTransactionHash());
//
//        //获取并打印存储的值
//        String value1 = contract.get("1").send();
//        System.out.println("Stored value for key '1': " + value1);
//
//        String value2 = contract.get("3").send();
//        System.out.println("Stored value for key '3': " + value2);

//        // 获取并打印存储的值
//        try {
//            String value1 = getValueForKey(contract, "2");
//            System.out.println("Stored value for key '1': " + value1);
//        } catch (ContractCallException e) {
//            System.err.println("Error retrieving value for key '1': " + e.getMessage());
//        }
//
//        try {
//            String value3 = getValueForKey(contract, "3");
//            System.out.println("Stored value for key '3': " + value3);
//        } catch (ContractCallException e) {
//            System.err.println("Error retrieving value for key '3': " + e.getMessage());
//        }
//    }

//    /**
//     * 根据键获取存储的值
//     *
//     * @param contract KeyValueStore合约实例
//     * @param key      键
//     * @return 存储的值
//     * @throws Exception 可能抛出的异常
//     */
//    public static String getValueForKey(KeyValueStore contract, String key) throws Exception {
//        String result = contract.get(key).send();
//        if (result == null || result.isEmpty()) {
//            throw new ContractCallException("Empty value (0x) returned from contract for key: " + key);
//        }
//        return result;
//    }
//}

public class test1 {
    private static final String GANACHE_URL = "http://113.54.204.20:7545";
    private static final String PRIVATE_KEY = "0x7ffc3861bc60b6133ff21f20d2046a18585a70197bc77caf369ec7359e112b91";
    private static final String CONTRACT_ADDRESS = "0xF11C8fb332129B532C3B1b734141CA2A44c52503";

    private static final BigInteger GAS_PRICE = BigInteger.valueOf(20000000000L);
    private static final BigInteger GAS_LIMIT = BigInteger.valueOf(6721975L);

    private static Web3j web3j = Web3j.build(new HttpService(GANACHE_URL));
    private static Credentials credentials = Credentials.create(PRIVATE_KEY);
    private static ContractGasProvider gasProvider = new StaticGasProvider(GAS_PRICE, GAS_LIMIT);
    private static KeyValueStore contract =  KeyValueStore.load(CONTRACT_ADDRESS, web3j, credentials, gasProvider.getGasPrice(), gasProvider.getGasLimit());


    public static String getValueForKey(String key) throws Exception {
        return contract.get(key).send();
    }

    public static String setValueForKey(String key, String value) throws Exception {
        TransactionReceipt receipt = contract.set(key, value).send();
        return receipt.getTransactionHash();
    }
}
