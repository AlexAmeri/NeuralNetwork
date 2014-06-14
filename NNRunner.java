import java.util.Scanner;
import java.util.Random;

public class NNRunner {

	/**
	 * @param args
	 */
	static int inputcount;
	static int hiddencount;
	static int outputcount;
	static int[] input;
	static double[] ideal;
	static double[] error = new double[4];
	
	static int[][] input_1 = new int[6][3];
	static double[] ideal_1 = new double[6];
	static double[][][] oldDelta = new double[4][4][4];
	static double[]bias = new double[2];
	
	
	public static void main(String[] args) {
		
		
		// Initialize network
		NeuralNetwork test = new NeuralNetwork(3,4,1,true);
		Scanner scan = new Scanner(System.in);
		System.out.println("How many input patterns? ");
		int setSize = scan.nextInt();
		
		
		//Get input values
		for(int i=0;i<setSize;i++){
			for(int x=0;x<2;x++){
				System.out.println("What is input #"+(x+1)+" for pattern #"+(i+1)+" ?");
				input_1[i][x]=scan.nextInt();
			}
			System.out.println("What is the desired output for pattern #"+(i+1)+" ?");
			ideal_1[i]=scan.nextDouble();
		}
		
		//Get training instructions
		System.out.println("How many times would you like to train? ");
		int trainingIterations = scan.nextInt();
		System.out.println("What is the learning rate?");
		double learningRate = scan.nextDouble();
		System.out.println("What is the momentum?");
		double momentum = scan.nextDouble();
		


		//Run and train the network
		test.setInput(2,1);
		for(int i=0;i<trainingIterations; i++){
						
			//One pattern at a time
			for(int t=0;t<setSize;t++){
				
				//Set the input values
				test.setInput(0, input_1[t][0]);
				test.setInput(1, input_1[t][1]);
																
				//Run the network
				test.setHidden();
				test.setOutput();
				
				//Calculate error
				double error = ((double)ideal_1[t])-test.getOutput(0);
			
				//back-propogate the error																		
				//adjust gradients		    
			    //output layer				   
				test.setGradOut(0,error*((1-test.getOutput(0))*test.getOutput(0)));
				   			   			   			 			   
			   	//hidden layer			   
			    for(int hh=0;hh<3;hh++){
			    		double deriv =test.getHidden(hh)*(1-test.getHidden(hh));
			    		double grad = deriv*(test.getWeightOut(hh, 0)*test.getGradOut(0));
			    		test.setGradIn(hh,grad);
			    	}
			    
			    
			    //set deltas
			    //hidden-output layer
			    for(int hhh=0;hhh<4;hhh++){
			    	double mom = momentum*oldDelta[1][hhh][0];
			    	double delta = learningRate*test.getGradOut(0)*test.getHidden(hhh);
			    	test.setWeightOut(hhh,0,delta+mom);
			    	oldDelta[1][hhh][0]=delta;
			    }
			    //input-output layer
			    for(int iii=0;iii<3;iii++){
			    	for(int hhh=0;hhh<3;hhh++){
			    		double mom = momentum*oldDelta[0][iii][hhh];
				    	double delta = learningRate*test.getGradIn(hhh)*input_1[t][iii];
				    	test.setWeightIn(iii,hhh,delta+mom);
				    	oldDelta[0][iii][hhh]=delta;			    	
			    	}
			 	}
		}
			System.out.println(test);
		}
		
		//Test!
		boolean exam=true;
		while(exam==true){		
		for(int i=0;i<2;i++){
			System.out.println("Input #"+(i+1));
			test.setInput(i, scan.nextInt());
		}
		test.setHidden();
		test.setOutput();
		System.out.println("The output is: "+test.getOutput(0));
		System.out.println("Test again? (1 for yes, 0 for no)");
		exam=(scan.nextInt()==1);
		}
		

}
}
