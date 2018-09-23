package com.technochord.blockchain.nukid;

import com.google.gson.GsonBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class ChainSaw implements CommandLineRunner {

    public static List<Block> blockchain = new ArrayList<Block>();
    public static int difficulty = 1;

    @Override
    public void run(String... args) throws Exception {

        //add our blocks to the blockchain ArrayList:
        blockchain.add(new Block("Hi im the first block", "0"));
        log.info("Trying to Mine block 1... ");
        blockchain.get(0).mineBlock(difficulty);

        blockchain.add(new Block("Yo im the second block",blockchain.get(blockchain.size()-1).hash));
        log.info("Trying to Mine block 2... ");
        blockchain.get(1).mineBlock(difficulty);

        blockchain.add(new Block("Hey im the third block",blockchain.get(blockchain.size()-1).hash));
        log.info("Trying to Mine block 3... ");
        blockchain.get(2).mineBlock(difficulty);

        String blockchainJson = new GsonBuilder().setPrettyPrinting().create().toJson(blockchain);
        log.debug(blockchainJson);

        log.debug("Chain valid? : " + isChainValid());
    }

    public static Boolean isChainValid() {
        Block currentBlock;
        Block previousBlock;

        //loop through blockchain to check hashes:
        for(int i=1; i < blockchain.size(); i++) {
            currentBlock = blockchain.get(i);
            previousBlock = blockchain.get(i-1);
            //compare registered hash and calculated hash:
            if(!currentBlock.hash.equals(currentBlock.calculateHash()) ){
                log.error("Current Hashes not equal");
                return false;
            }
            //compare previous hash and registered previous hash
            if(!previousBlock.hash.equals(currentBlock.previousHash) ) {
                log.error("Previous Hashes not equal");
                return false;
            }
        }
        return true;
    }
}
