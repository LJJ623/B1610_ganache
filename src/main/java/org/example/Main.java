package org.example;

import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.Web3ClientVersion;
import org.web3j.protocol.http.HttpService;

import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        // Ganache默认的HTTP地址
        String ganacheUrl = "http://113.54.211.176:7545";

        // 创建Web3j对象来连接Ganache
        Web3j web3j = Web3j.build(new HttpService(ganacheUrl));

        try {
            // 获取客户端版本信息
            Web3ClientVersion clientVersion = web3j.web3ClientVersion().send();
            String clientVersionString = clientVersion.getWeb3ClientVersion();
            System.out.println("Connected to Ethereum client version: " + clientVersionString);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error connecting to Ganache: " + e.getMessage());
        }
    }
}
