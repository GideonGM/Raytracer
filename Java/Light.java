public class Light{
        String type;
        float intensity;
        float[] position;
	float[] direction;

	public Light(String t, float i){
		this.type = t;
		this.intensity = i;
	}
        public Light(String t, float i, float[] pOrD){
		this.type = t;
		this.intensity = i;
		switch (t){
			case "point":
				this.position = pOrD;
				break;
			case "directional":
				this.direction = pOrD;
				break;
			default:
				throw new IllegalArgumentException("Type must be \"point\" or \"directional\"");
		}
        }

}
