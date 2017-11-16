package com.risk.jacksontest;

import java.io.File;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonMethod;
import org.codehaus.jackson.annotate.JsonAutoDetect.Visibility;
import org.codehaus.jackson.map.ObjectMapper;

import com.risk.entity.Map;
import com.risk.entity.Player;
import com.risk.model.GameModel;
import com.risk.model.PlayerWorldDomination;

@JsonAutoDetect
public class JacksonTest {
	/**
	 * The @map reference.
	 */
	private Map map;

	/**
	 * The @gameModel reference.
	 */
	private GameModel gameModel;

	/**
	 * The @worldDomination.
	 */
	private PlayerWorldDomination worldDomination;

	/**
	 * The @playerPlaying current player playing.
	 */
	private Player playerPlaying;

	public JacksonTest(Map map, GameModel gameModel,PlayerWorldDomination worldDomination,Player playerPlaying) {
		this.map = map;
		this.gameModel = gameModel;
		this.worldDomination = worldDomination;
		this.playerPlaying = playerPlaying;
	}

	//Introducing the dummy constructor to remove org.codehaus.jackson.map.JsonMappingException error
	public JacksonTest() {
	}

	ObjectMapper mapper = new ObjectMapper();
	//Getting no javadoc suggestions for mapper, its present under Maven Dependencies, also in pom.xml
	//Uncomment following line to see the error
	//		mapper.setVisibility(JsonMethod.FIELD, Visibility.ANY);
	//Object to JSON in file
	//	mapper.writeValue(new File("player.json"), player);
	//JSON from file to Object
	//			Player playerFromFile2Obj = mapper.readValue(new File("player.json"), Player.class);
}
