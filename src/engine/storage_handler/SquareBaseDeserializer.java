package engine.storage_handler;

import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import engine.*;

// TODO: Javadoc kontrollieren
/**
 * The Class SquareBaseDeserializer.
 */
class SquareBaseDeserializer implements JsonDeserializer<SquareBase> {
	
	/**
	 * @see com.google.gson.JsonDeserializer#deserialize(com.google.gson.JsonElement, java.lang.reflect.Type, com.google.gson.JsonDeserializationContext)
	 */
	public SquareBase deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {	    
		JsonObject jsonObject = json.getAsJsonObject();
		if(jsonObject.has("number")){
			if(jsonObject.has("original_number"))
				return new NumberSquare(jsonObject.get("original_number").getAsInt(), jsonObject.get("posX").getAsInt(), jsonObject.get("posY").getAsInt());
			else
				return new NumberSquare(jsonObject.get("number").getAsInt(), jsonObject.get("posX").getAsInt(), jsonObject.get("posY").getAsInt());
		}else if(jsonObject.has("direction") && jsonObject.has("lightsourcePositionX") && jsonObject.has("lightsourcePositionY")){
			RaySquare square =  new RaySquare(jsonObject.get("posX").getAsInt(), jsonObject.get("posY").getAsInt());
			square.lightsourcePositionX = Integer.parseInt(jsonObject.get("lightsourcePositionX").toString());
			square.lightsourcePositionY = Integer.parseInt(jsonObject.get("lightsourcePositionY").toString());
			return square;
		}
		else{
			return new UntypedSquare(jsonObject.get("posX").getAsInt(), jsonObject.get("posY").getAsInt());
		}
	}
}
