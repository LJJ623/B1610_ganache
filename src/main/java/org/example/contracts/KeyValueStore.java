package org.example.contracts;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the 
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 3.2.0.
 */
public class KeyValueStore extends Contract {
    private static final String BINARY = "0x608060405234801561001057600080fd5b506105c0806100206000396000f3fe608060405234801561001057600080fd5b50600436106100365760003560e01c8063693ec85e1461003b578063e942b5161461006b575b600080fd5b6100556004803603810190610050919061036b565b610087565b604051610062919061043c565b60405180910390f35b6100856004803603810190610080919061045e565b610137565b005b60606000826040516100999190610512565b908152602001604051809103902080546100b290610558565b80601f01602080910402602001604051908101604052809291908181526020018280546100de90610558565b801561012b5780601f106101005761010080835404028352916020019161012b565b820191906000526020600020905b81548152906001019060200180831161010e57829003601f168201915b50505050509050919050565b806000836040516101489190610512565b9081526020016040518091039020908051906020019061016992919061016e565b505050565b82805461017a90610558565b90600052602060002090601f01602090048101928261019c57600085556101e3565b82601f106101b557805160ff19168380011785556101e3565b828001600101855582156101e3579182015b828111156101e25782518255916020019190600101906101c7565b5b5090506101f091906101f4565b5090565b5b8082111561020d5760008160009055506001016101f5565b5090565b6000604051905090565b600080fd5b600080fd5b600080fd5b600080fd5b6000601f19601f8301169050919050565b7f4e487b7100000000000000000000000000000000000000000000000000000000600052604160045260246000fd5b6102788261022f565b810181811067ffffffffffffffff8211171561029757610296610240565b5b80604052505050565b60006102aa610211565b90506102b6828261026f565b919050565b600067ffffffffffffffff8211156102d6576102d5610240565b5b6102df8261022f565b9050602081019050919050565b82818337600083830152505050565b600061030e610309846102bb565b6102a0565b90508281526020810184848401111561032a5761032961022a565b5b6103358482856102ec565b509392505050565b600082601f83011261035257610351610225565b5b81356103628482602086016102fb565b91505092915050565b6000602082840312156103815761038061021b565b5b600082013567ffffffffffffffff81111561039f5761039e610220565b5b6103ab8482850161033d565b91505092915050565b600081519050919050565b600082825260208201905092915050565b60005b838110156103ee5780820151818401526020810190506103d3565b838111156103fd576000848401525b50505050565b600061040e826103b4565b61041881856103bf565b93506104288185602086016103d0565b6104318161022f565b840191505092915050565b600060208201905081810360008301526104568184610403565b905092915050565b600080604083850312156104755761047461021b565b5b600083013567ffffffffffffffff81111561049357610492610220565b5b61049f8582860161033d565b925050602083013567ffffffffffffffff8111156104c0576104bf610220565b5b6104cc8582860161033d565b9150509250929050565b600081905092915050565b60006104ec826103b4565b6104f681856104d6565b93506105068185602086016103d0565b80840191505092915050565b600061051e82846104e1565b915081905092915050565b7f4e487b7100000000000000000000000000000000000000000000000000000000600052602260045260246000fd5b6000600282049050600182168061057057607f821691505b6020821081141561058457610583610529565b5b5091905056fea264697066735822122007e37c3ad5ab35e64a167103ee9c0acee3d858f3593c2d2d3fb95c8a81e6a76964736f6c634300080b0033";

    protected static final HashMap<String, String> _addresses;

    static {
        _addresses = new HashMap<>();
        _addresses.put("5777", "0x4BAD2DAC98c25e1c4B49cD7270b5d1EE6454a90F");
    }

    protected KeyValueStore(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected KeyValueStore(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public RemoteCall<TransactionReceipt> set(String key, String value) {
        Function function = new Function(
                "set", 
                Arrays.<Type>asList(new Utf8String(key),
                new Utf8String(value)),
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<String> get(String key) {
        Function function = new Function(
                "get",
                Arrays.<Type>asList(new Utf8String(key)),
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }


    public static RemoteCall<KeyValueStore> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(KeyValueStore.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    public static RemoteCall<KeyValueStore> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(KeyValueStore.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }

    public static KeyValueStore load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new KeyValueStore(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    public static KeyValueStore load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new KeyValueStore(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected String getStaticDeployedAddress(String networkId) {
        return _addresses.get(networkId);
    }

    public static String getPreviouslyDeployedAddress(String networkId) {
        return _addresses.get(networkId);
    }
}
