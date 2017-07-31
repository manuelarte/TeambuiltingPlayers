package org.manuel.teambuilting.players;

import org.manuel.teambuilting.core.config.EnableCoreFunctionalities;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
@EnableCoreFunctionalities
public class TeamBuiltingPlayersApplication {

	public static void main(final String[] args) {
		SpringApplication.run(TeamBuiltingPlayersApplication.class, args);
	}

}
