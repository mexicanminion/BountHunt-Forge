package net.mexicanminion.bountyhunt.managers;

import java.io.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.UUID;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import net.mexicanminion.bountyhunt.util.BountyData;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;

public class BountyDataManager {
    public static Stack<BountyData> bountyData = new Stack<>();

    int bountyCapacity = bountyData.capacity();
    int totalVarsStored = 10;

    public void saveBountyDataFile(Logger logger) throws FileNotFoundException, IOException {
        File bountyDir = Paths.get("", "bountyhunt").toFile();
        File file = new File(bountyDir, "bountyData.dat");
        ObjectOutputStream output = new ObjectOutputStream(new GZIPOutputStream(new FileOutputStream(file)));

        String[][] allDataArray = new String[bountyCapacity][totalVarsStored];

        int currCount = 0;
        for(BountyData data : bountyData){
            allDataArray[currCount] = data.getSaveData();
            currCount++;
        }

        try{
            output.writeObject(allDataArray);
            output.flush();
            output.close();
            logger.info("Saved BountyHunt files.");
        } catch(IOException e){
            e.printStackTrace();
            logger.info("Failed to save BountyHunt files.");
        }
    }

    /**
     * loadBountyDataFile()
     * Description: Load the bounty file
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public void loadBountyDataFile() throws IOException, ClassNotFoundException {
        File bountyDir = Paths.get("", "bountyhunt").toFile();
        File file = new File(bountyDir, "bountyData.dat");

        if(file != null){
            ObjectInputStream input = new ObjectInputStream(new GZIPInputStream(new FileInputStream(file)));
            Object readObject = input.readObject();
            input.close();

            if(!(readObject instanceof String[][])){
                throw new IOException("Data is not a String[][]");
            }

            bountyData.empty();

            String[][] allDataArray = (String[][]) readObject;
            //logger.info(Arrays.deepToString(allDataArray));

            for(int i = 0; i < allDataArray.length; i++){
                if(allDataArray[i][0] != null){
                    bountyData.add(new BountyData(allDataArray[i]));
                }
            }

        }
    }

    public void updateAndLoadBDFile() throws IOException, ClassNotFoundException {
        File bountyDir = Paths.get("", "bountyhunt").toFile();
        File file = new File(bountyDir, "bountyData.dat");

        if(file != null){
            ObjectInputStream input = new ObjectInputStream(new GZIPInputStream(new FileInputStream(file)));
            Object readObject = input.readObject();
            input.close();

            if(!(readObject instanceof String[][])){
                throw new IOException("Data is not a String[][]");
            }

            String[][] allDataArray;
            bountyData.empty();
            allDataArray = (String[][]) readObject;

            int arrayDiff = new BountyData().getSaveData().length - allDataArray[0].length;
            String[] temp = new String[arrayDiff];

            //fill temp array with null vales
            for(int i = 0; i < arrayDiff; i++){
                temp[i] = null;
            }

            for(int r = 0; r < allDataArray.length; r++){
                if(allDataArray[r][0] != null){
                    bountyData.add(new BountyData(ArrayUtils.addAll(allDataArray[r], temp)));
                }
            }

        }
    }

    public static void setBountyData(BountyData playerData){
        bountyData.add(playerData);
    }

    public static BountyData getBountyData(UUID player){
        for (BountyData data:bountyData) {
            if(data.getUUID().equals(player)){
                return data;
            }
        }
        return null;
    }

    public static List<BountyData> getBountyData(){
        List<BountyData> bountyList = new ArrayList<>();
        for (BountyData data:bountyData) {
            if(data.getHasBounty()){
                bountyList.add(data);
            }
        }
        return bountyList;
    }

    public static int getActiveBountyAmount(){
        int activeBountyAmount = 0;
        for (BountyData data:bountyData) {
            if(data.getHasBounty()){
                activeBountyAmount++;
            }
        }

        return activeBountyAmount;
    }
}
