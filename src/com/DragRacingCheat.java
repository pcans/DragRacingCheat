package com;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.ByteBuffer;

public class DragRacingCheat {
	
	
	public static class DragRacingException extends Exception {

		public DragRacingException(String msg, Exception e) {
			super("ERROR: " + msg, e);
		}
		
		public DragRacingException(String msg) {
			super("ERROR: " + msg);
		}
	}

	public static class Context {
		int money;
		File input;
		File output;
		
		public Context(int pMoney, File pInput, File pOutput) {
			this.money = pMoney;
			this.input = pInput;
			this.output = pOutput;
		}
	}

	/** reverse engineered from com.creativemobile.dragracingbe.model.JavaSettingsCrypt dex file
	 * @param key
	 * @param data
	 * @return
	 */
	public static byte[] decode(String key, byte[] data) {
		
		byte[] keyData = key.getBytes();
		keyData = key.getBytes();
		byte keyXor = keyData[0];
		int dataLen = data.length;
		if (dataLen >= keyData.length) {
			byte newKeyXor = 0;
			
			//xor everything with key
			for (int i=0; i < dataLen; i++) {
			    newKeyXor = data[i];
			    data[i] = (byte) (data[i] ^ keyXor);
			    keyXor = newKeyXor;
			}
			
			//reverse data
			int dataLength = data.length;
			for (int i=0; i < (dataLength >> 1); i++)  {
				byte tmp = data[i];
			    data[i] = data[dataLength - i - 1];
			    data[dataLength - i - 1] = tmp;
			}
			
			//xor again, cuz I'm worth it.
			keyXor = keyData[0];
			for (int i=0; i < dataLen; i++) {
			    newKeyXor = data[i];
			    data[i] = (byte)(data[i] ^ keyXor);
			    keyXor = newKeyXor;
			}
			
			//ensure data correctly decoded
			int resultDataLen = dataLen - keyData.length;
			for (int i=0; i < keyData.length; i++) {
			    if (keyData[i] != data[i + resultDataLen]) {
			    	return null;
			    }
			}
			byte [] result = new byte[resultDataLen];
			System.arraycopy(data, 0, result, 0, result.length);
			return result;
		
		}
	   	return null;
	}
	
	/** reverse engineered from com.creativemobile.dragracingbe.model.JavaSettingsCrypt dex file
	 * @param key
	 * @param data
	 * @return
	 */
	public static byte[] encode(String key, byte[] data) {
	    //.locals 11
	    //.parameter "key"
	    //.parameter "data"
		//.local v0, dataLen:I
	    //.local v2, keyData:[B
		//.local v4, result:[B
		//.local v3, keyXor:B
	    
	    //.prologue
	    //const/4 v10, 0x1
		//const/4 v9, 0x0
		//.line 5
	    //invoke-virtual {p0}, Ljava/lang/String;->getBytes()[B                               
	    //move-result-object v2
	    byte[] keyData = key.getBytes();
	    //.line 7
	    //.local v2, keyData:[B
	    //array-length v0, p1
	    int dataLen = data.length;
	    //.line 8
	    //.local v0, dataLen:I
	    //array-length v7, v2
	    //add-int/2addr v7, v0
	    //new-array v4, v7, [B
	    byte[] result = new byte[keyData.length + dataLen];
	    //.line 10
	    //.local v4, result:[B
	    //aget-byte v3, v2, v9
	    byte keyXor = keyData[0];
	    //.line 13
	    //.local v3, keyXor:B
	    
	    //const/4 v1, 0x0
	    //.local v1, i:I
	    //:goto_0
	    //if-lt v1, v0, :cond_0
	    for (int i = 0;i < dataLen; i++) {
	    	//goto :cond_0;
	
	    	//.line 14
	        //.end local v5           #resultLength:I
	    	//:cond_0
	        //aget-byte v7, p1, v1
	        //xor-int/2addr v7, v3
	        //int-to-byte v7, v7
	        //aput-byte v7, v4, v1
	        result[i] = (byte)(data[i]^keyXor);
	        //.line 15
	        //aget-byte v3, v4, v1
	        keyXor = result[i];
	        //.line 13
	        //add-int/lit8 v1, v1, 0x1
	        //goto :goto_0
	    }
	    
	    

		//.local v0, dataLen:I
	    //.local v2, keyData:[B
		//.local v4, result:[B
		//.local v3, keyXor:B
	    
	    //.line 17
	    //const/4 v1, 0x0
	    //:goto_1;
	    //array-length v7, v2
	    //if-lt v1, v7, :cond_1
	    for (int i = 0; i < keyData.length; i++) {
	    	//goto :cond_1;
	    	//.line 18
	        //:cond_1
	        //add-int v7, v1, v0
	    	//aget-byte v8, v2, v1
	    	//xor-int/2addr v8, v3
	    	//int-to-byte v8, v8
	        //aput-byte v8, v4, v7
	    	result[i + dataLen] = (byte) (keyData[i] ^ keyXor);
	        //.line 19
	        //add-int v7, v1, v0
	        //aget-byte v3, v4, v7
	        keyXor = result[i + dataLen];
	        //.line 17
	        //add-int/lit8 v1, v1, 0x1
	        //goto :goto_1
	    }
	
	    //.line 22
	    //array-length v5, v4
	    int resultLength = result.length;
	    //.line 24
	    //.local v5, resultLength:I
	    //const/4 v1, 0x0
	    //:goto_2
	    //shr-int/lit8 v7, v5, 0x1
	    //if-lt v1, v7, :cond_2
	    for (int i = 0; i < (resultLength >> 1); i++) {
	    	//goto :cond_2;
	    	
	    	//.line 25
	        //.restart local v5       #resultLength:I
	        //:cond_2
	        //aget-byte v6, v4, v1
	        byte tmp = result[i];
	        //.line 26
	        //.local v6, tmp:B
	        //sub-int v7, v5, v1
	        //sub-int/2addr v7, v10
	        //aget-byte v7, v4, v7
	        //aput-byte v7, v4, v1
	        result[i] = result[resultLength - i - 1];
	        //.line 27
	        //sub-int v7, v5, v1
	        //sub-int/2addr v7, v10
	        //aput-byte v6, v4, v7
	        result[resultLength - i - 1] = tmp;
	        //.line 24
	        //add-int/lit8 v1, v1, 0x1
	        //goto :goto_2
	    }
	    
	    //.line 30
	    //aget-byte v3, v2, v9
	    keyXor = keyData[0];
	    //.line 31
	    //const/4 v1, 0x0
	    //:goto_3
	    //if-lt v1, v5, :cond_3
	    for (int i=0; i < resultLength; i++) {
	    	//goto :cond_3;
	    	//.line 32
	        //.end local v6           #tmp:B
	        //:cond_3
	        //aget-byte v7, v4, v1
	        //xor-int/2addr v7, v3
	        //int-to-byte v7, v7
	        //aput-byte v7, v4, v1
	        result[i] = (byte)(result[i] ^ keyXor);
	        //.line 33
	        //aget-byte v3, v4, v1
	        keyXor = result[i];
	        //.line 31
	        //add-int/lit8 v1, v1, 0x1
	        //goto :goto_3
	    }
	
	    //.line 36
	    //return-object v4
	    return result;
	}
	
	public static void printUsage() {
		System.out.println("Usage: DragRacingCheat input value output");
		System.out.println("  input: Svf input file to read from.");
		System.out.println("  value: The new value for the money.");
		System.out.println("  output: Svf output file to write to.");
	}
	
	public static void main(String[] args){
		Context ctx = validateArgs(args);
		String key = "Drag Racing Bikes";
		
		if (ctx != null) {
			try {
				System.out.println("Open and extract data from input file..."); 
				byte[] gameData = unseal(ctx.input);
				
				System.out.println("Decode gameData...");
				byte[] decodedGameData = decode(key, gameData);
				if (decodedGameData == null) {
					throw new DragRacingException("unable to decode file");
				}
				int actualMoney = ((0xFF & decodedGameData[30]) << 24) 
						| ((0xFF & decodedGameData[31]) << 16)
						| ((0xFF & decodedGameData[32]) << 8)
						| (0xFF & decodedGameData[33]);
				
				System.out.println(" -> Actual money: " + actualMoney);
				
				System.out.println("Patch gameData...");
				decodedGameData[30] = (byte)(ctx.money >>> 24);
				decodedGameData[31] = (byte)(ctx.money >>> 16);
				decodedGameData[32] = (byte)(ctx.money >>> 8);
				decodedGameData[33] = (byte)ctx.money;
				System.out.println(" -> New money: " + ctx.money);
				
				System.out.println("Encode gameData...");
				gameData = encode(key, decodedGameData);
				
				System.out.println("Save data to binary out file...");
				seal(gameData, ctx.output);
				System.out.println(" -> file "+ctx.output.getAbsolutePath()+" written");
				
			
			} catch (DragRacingException e) {
				System.err.println(e.getMessage());
				if (e.getCause() != null) {
					e.printStackTrace();
				}
			}
		}
	}
	
	private static void seal(byte[] gameData, File output) throws DragRacingException {
		FileOutputStream fileOut = null;
		ObjectOutputStream out = null;
		try {
			fileOut = new FileOutputStream(output);
			out = new ObjectOutputStream(fileOut);
			out.writeObject(gameData);
		} catch (FileNotFoundException e) {
			throw new DragRacingCheat.DragRacingException("unable to write to output file " + output.getAbsolutePath());
		} catch (IOException ioe) {
			throw new DragRacingCheat.DragRacingException("unable to write to output file " + output.getAbsolutePath(), ioe);
		}

		try {
			if (out != null) {
				out.close();
			}
			if (fileOut != null) {
				fileOut.close();
			}
		} catch (IOException e) {
			//whatever
		}
	}

	private static byte[] unseal(File input) throws DragRacingException {
		FileInputStream fileIn;
		ObjectInputStream in;
		byte[] gameData = null;
		try {
			fileIn = new FileInputStream(input);
			in = new ObjectInputStream(fileIn);
			gameData = (byte[])in.readObject();
		} catch (FileNotFoundException fnfe) {
			throw new DragRacingCheat.DragRacingException("unable to read input file", fnfe);
		} catch (IOException ioe) {
			throw new DragRacingCheat.DragRacingException("unable to read input file", ioe);
		} catch (ClassNotFoundException cnfe) {
			throw new DragRacingCheat.DragRacingException("invalid input file format", cnfe);
		}
		 return gameData;
	}

	
	private static Context validateArgs(String[] args) {
		if (args.length < 2) {
			System.err.println("ERROR: missing parameters");
			printUsage();
			return null;
		}
		int money = 0;
		File input = new File(args[0]);
		try {
			money = Integer.parseInt(args[1]);
		} catch(NumberFormatException nfe) {
			System.err.println("ERROR: value is not a valid integer");
			printUsage();
			return null;
		}
		if (!input.isFile()) {
			System.err.println("ERROR: input file "+args[0]+" doesn't exists.");
			printUsage();
			return null;
		}
		File output = new File(args[2]);
		return new DragRacingCheat.Context(money, input, output);
	}

	public static void printArray(byte[] data) {
		for (int j = 0; j < data.length; j++) {
			   System.out.format("%02X ", data[j]);
			}
			System.out.println();
	}
	
	public static void saveData(byte[] data) {
		 try
	     {
			 File f = new File("out.bin");
			 FileOutputStream fos = new FileOutputStream(f);
	       fos.write(data);
	       fos.close();
	       System.out.println("Data written to file " + f.getAbsolutePath());
	     
	     }
	     catch(FileNotFoundException ex)
	     {
	      System.out.println("FileNotFoundException : " + ex);
	     }
	     catch(IOException ioe)
	     {
	      System.out.println("IOException : " + ioe);
	     }
	}
	
}
