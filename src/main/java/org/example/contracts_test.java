package org.example;

import org.example.contracts.Helloworld;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;
import org.web3j.crypto.Credentials;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.tx.gas.StaticGasProvider;

import java.math.BigInteger;

public class contracts_test {
    private static final String GANACHE_URL = "http://113.54.199.66:7545"; // 你的Ganache节点地址
    private static final String PRIVATE_KEY = "0x6c8cc4064ecee40cea80d4e949f24ad1bd13071d6a098d95c285a8e586f574f2"; // 你的账户私钥
    private static final String CONTRACT_ADDRESS = "0xfD37C114D917548CC289F3A679Ce8924463389DB"; // 你的合约地址

    // 手动设置Gas限制
    private static final BigInteger GAS_PRICE = BigInteger.valueOf(20000000000L); // 20 Gwei
    private static final BigInteger GAS_LIMIT = BigInteger.valueOf(6721975L); // 可以设置为Ganache的默认上限

    public static void main(String[] args) throws Exception {
        // 连接到Ganache
        Web3j web3j = Web3j.build(new HttpService(GANACHE_URL));

        // 加载凭证
        Credentials credentials = Credentials.create(PRIVATE_KEY);

        // 设置Gas Provider
        ContractGasProvider gasProvider = new StaticGasProvider(GAS_PRICE, GAS_LIMIT);

        // 加载合约
        Helloworld contract = Helloworld.load(CONTRACT_ADDRESS, web3j, credentials, gasProvider.getGasPrice(), gasProvider.getGasLimit());

        // 调用合约方法并获取返回值
        String message = String.valueOf(contract.say().send());
        System.out.println("Contract message: " + message);
    }
}
