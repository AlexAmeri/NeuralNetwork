import java.util.Random;

public class NeuralNetwork {
	
	private double[][] weightsIn;
	private double[][] weightsOut;
	private int[] input;
	private double[] hidden;
	private double[] output;
	private double[][] bias;
	private double[] gradOut;
	private double[] gradIn;
	private boolean isBiased;
	
	public NeuralNetwork(int inputCount, int hiddenCount, int outputCount, boolean biased){
		
		// Initialize the network
		input = new int[inputCount];
		hidden= new double[hiddenCount];
		output = new double[outputCount];
		weightsIn = new double[inputCount][hiddenCount];
		weightsOut = new double[hiddenCount][outputCount];
		bias = new double[2][(hiddenCount>outputCount) ? hiddenCount:outputCount];
		gradOut = new double[outputCount];
		gradIn = new double[hiddenCount];
		isBiased = biased;
		
		// Initialize the weights
		Random firstWeight = new Random();
		for(int i=0;i<weightsIn.length;i++){
			for(int o=0; o<weightsIn[0].length;o++){
				
				weightsIn[i][o] = firstWeight.nextInt(100)*.1-5;
			}
		}
		
		for(int i=0;i<weightsOut.length;i++){
			for(int o=0; o<weightsOut[0].length;o++){
				
				weightsOut[i][o] = firstWeight.nextInt(100)*.1-5;
			}
		}
	}
	
	public void adjustBias(int layer, int connection,double delta){
		bias[layer][connection]+=delta;
	}
	
	
	
	public void setGradIn(int hid, double value){
		gradIn[hid]=value;
	}
	
	public void setGradOut(int out, double value){
		gradOut[out]=value;
	}
	
	public double getGradIn(int in){
		return gradIn[in];
	}
	
	public double getGradOut(int out){
		return gradOut[out];
	}
	
	public double getBiasWeight(int layer, int connection){
		return bias[layer][connection];
	}
	
	public void setWeightIn(int inputt, int hiddenn, double delta){
		weightsIn[inputt][hiddenn]+=delta;
	}
	
	public void setWeightOut(int hiddenn, int outputt, double delta){
		weightsOut[hiddenn][outputt]+=delta;
	}
	
	public double getWeightIn(int inputt, int hiddenn){
		return weightsIn[inputt][hiddenn];
	}
	
	public double getWeightOut(int hiddenn, int outputt){
		return weightsOut[hiddenn][outputt];
	}
	
	public void setInput(int inputt, int value){
		input[inputt]=value;
	}
	
	//This will allow manual change of hidden neuron values
	public void changeHidden(int hiddenn, double value){
		hidden[hiddenn]=value;
	}
	
	//This will run during calculation to set hidden neuron values based on input neuron values
	public void setHidden(){
		
		for(int h=0;h<((isBiased==true) ? hidden.length-1:hidden.length);h++){
			double hiddenValue=0.0;
			for(int i=0; i<input.length;i++){
				hiddenValue+=(input[i]*weightsIn[i][h]);
			}
			hidden[h]=(1.0/(1.0+Math.pow(2.718,(-1.0*hiddenValue))));
		}
		
		//Set bias if there is one
		if(isBiased){
			hidden[hidden.length-1]=1;
		}
		
	}
	
	//This will run during calculation to set output neuron values based on hidden neuron values
		public void setOutput(){
			
			for(int o=0;o<output.length;o++){
				double outputValue=0.0;
				for(int h=0; h<hidden.length;h++){
					outputValue+=(hidden[h]*weightsOut[h][o]);
				}
				output[o]=(1.0/(1.0+Math.pow(2.718,(-1.0*outputValue))));
			}
		}
	
	public double getHidden(int hiddenn){
		return hidden[hiddenn];
	}
	
	public double getOutput(int outputt){
		return output[outputt];
	}
	
	public String toString()
	{
		String answer=("");
		for(int o=0;o<output.length;o++){
			answer+=("GradOut at "+o+" = "+getGradOut(o));
		}
		for(int o=0;o<hidden.length;o++){
			answer+=("GradIn at "+o+" = "+getGradIn(o));
		}
		return answer;
	}
	
	
	

}
