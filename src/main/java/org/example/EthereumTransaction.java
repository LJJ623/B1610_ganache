package org.example;

import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.EthSendTransaction;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.core.methods.response.EthGetTransactionCount;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.RawTransactionManager;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.tx.gas.DefaultGasProvider;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.RawTransaction;
import org.web3j.crypto.TransactionEncoder;
import org.web3j.utils.Convert;
import org.web3j.utils.Numeric;

import java.math.BigDecimal;
import java.math.BigInteger;

public class EthereumTransaction {

    public static void main(String[] args) {
        // Ganache HTTP URL
        String ganacheUrl = "http://113.54.217.70:7545";

        // 创建Web3j对象来连接Ganache
        Web3j web3j = Web3j.build(new HttpService(ganacheUrl));

        // 使用Ganache UI中的私钥创建Credentials对象
        String privateKey = "0xc105bae5c90994c34fe3ccf100592f9a348d329be3b180f6057437fabc5c1d75"; // 用你从Ganache复制的私钥替换
        Credentials credentials = Credentials.create(privateKey);

        // 交易参数
        String recipientAddress = "0x998605E68aDDC992Ac94B53e543Ca376d1312410"; // 接收者的地址
        BigInteger value = Convert.toWei("2", Convert.Unit.ETHER).toBigInteger(); // 转账金额
        BigInteger gasLimit = BigInteger.valueOf(21000); // 通常的转账交易的gas limit
        BigInteger gasPrice = DefaultGasProvider.GAS_PRICE;

        try {
            // 获取当前账户的nonce
            BigInteger nonce = web3j.ethGetTransactionCount(credentials.getAddress(), DefaultBlockParameterName.LATEST)
                    .send()
                    .getTransactionCount();
            //System.out.println("\n\n" + nonce.toString() +  "\n\n");

            // 创建交易对象
            RawTransaction rawTransaction = RawTransaction.createEtherTransaction(
                    nonce, gasPrice, gasLimit, recipientAddress, value);

            // 对交易进行签名
            byte[] signedMessage = TransactionEncoder.signMessage(rawTransaction, credentials);
            String hexValue = Numeric.toHexString(signedMessage);

            // 发送交易
            EthSendTransaction ethSendTransaction = web3j.ethSendRawTransaction(hexValue).send();
            String transactionHash = ethSendTransaction.getTransactionHash();

            System.out.println("Transaction hash: " + transactionHash);

            // 等待交易完成
            TransactionReceipt transactionReceipt = web3j.ethGetTransactionReceipt(transactionHash)
                    .send()
                    .getTransactionReceipt()
                    .orElseThrow(() -> new RuntimeException("Transaction not found"));

            System.out.println("Transaction complete: " + transactionReceipt.isStatusOK());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
