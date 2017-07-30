package org.manuel.teambuilting.players;

import org.manuel.teambuilting.core.config.CoreConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@EnableAspectJAutoProxy
@Import({CoreConfig.class})
public class TeamBuiltingPlayersApplication {

	public static void main(final String[] args) {
		SpringApplication.run(TeamBuiltingPlayersApplication.class, args);
	}

}
