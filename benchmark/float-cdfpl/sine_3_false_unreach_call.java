class sine_3_false_unreach_call
{
	public float sine_3_false_unreach_call(float IN) 
	{
		
		float x = IN;
		float result = x - (x*x*x)/6 + (x*x*x*x*x)/120 + (x*x*x*x*x*x*x)/5040;
		
		return result;
	}
}