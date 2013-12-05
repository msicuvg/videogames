struct C2E1v_Output{
  float4 position : POSITION;
  float4 color : COLOR;
};

C2E1v_Output C2E1v_green(float3 position : POSITION)
{
	C2E1V_Output salida;
	
	OUT.position = float4 (position,1);
	OUT.color = float4 (0,1,0,1);
	
	return salida;

}