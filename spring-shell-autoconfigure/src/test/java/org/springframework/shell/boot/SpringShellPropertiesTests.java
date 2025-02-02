/*
 * Copyright 2021-2022 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.shell.boot;

import org.junit.jupiter.api.Test;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;

import static org.assertj.core.api.Assertions.assertThat;

public class SpringShellPropertiesTests {

	private final ApplicationContextRunner contextRunner = new ApplicationContextRunner();

	@Test
	public void defaultNoPropertiesSet() {
		this.contextRunner
				.withUserConfiguration(Config1.class)
				.run((context) -> {
					SpringShellProperties properties = context.getBean(SpringShellProperties.class);
					assertThat(properties.getScript().isEnabled()).isTrue();
					assertThat(properties.getInteractive().isEnabled()).isTrue();
					assertThat(properties.getNoninteractive().isEnabled()).isTrue();
					assertThat(properties.getTheme().getName()).isEqualTo("default");
					assertThat(properties.getCommand().getClear().isEnabled()).isTrue();
					assertThat(properties.getCommand().getHelp().isEnabled()).isTrue();
					assertThat(properties.getCommand().getHistory().isEnabled()).isTrue();
					assertThat(properties.getCommand().getQuit().isEnabled()).isTrue();
					assertThat(properties.getCommand().getScript().isEnabled()).isTrue();
					assertThat(properties.getCommand().getStacktrace().isEnabled()).isTrue();
					assertThat(properties.getCommand().getCompletion().isEnabled()).isTrue();
					assertThat(properties.getCommand().getCompletion().getRootCommand()).isNull();
					assertThat(properties.getCommand().getVersion().isEnabled()).isTrue();
					assertThat(properties.getCommand().getVersion().getTemplate()).isNotNull();
					assertThat(properties.getCommand().getVersion().isShowBuildArtifact()).isFalse();
					assertThat(properties.getCommand().getVersion().isShowBuildGroup()).isFalse();
					assertThat(properties.getCommand().getVersion().isShowBuildName()).isFalse();
					assertThat(properties.getCommand().getVersion().isShowBuildTime()).isFalse();
					assertThat(properties.getCommand().getVersion().isShowBuildVersion()).isTrue();
					assertThat(properties.getCommand().getVersion().isShowGitBranch()).isFalse();
					assertThat(properties.getCommand().getVersion().isShowGitCommitId()).isFalse();
					assertThat(properties.getCommand().getVersion().isShowGitShortCommitId()).isFalse();
					assertThat(properties.getCommand().getVersion().isShowGitCommitTime()).isFalse();
				});
	}

	@Test
	public void setProperties() {
		this.contextRunner
				.withPropertyValues("spring.shell.script.enabled=false")
				.withPropertyValues("spring.shell.interactive.enabled=false")
				.withPropertyValues("spring.shell.noninteractive.enabled=false")
				.withPropertyValues("spring.shell.theme.name=fake")
				.withPropertyValues("spring.shell.command.clear.enabled=false")
				.withPropertyValues("spring.shell.command.help.enabled=false")
				.withPropertyValues("spring.shell.command.history.enabled=false")
				.withPropertyValues("spring.shell.command.quit.enabled=false")
				.withPropertyValues("spring.shell.command.script.enabled=false")
				.withPropertyValues("spring.shell.command.stacktrace.enabled=false")
				.withPropertyValues("spring.shell.command.completion.enabled=false")
				.withPropertyValues("spring.shell.command.completion.root-command=fake")
				.withPropertyValues("spring.shell.command.version.enabled=false")
				.withPropertyValues("spring.shell.command.version.template=fake")
				.withPropertyValues("spring.shell.command.version.show-build-artifact=true")
				.withPropertyValues("spring.shell.command.version.show-build-group=true")
				.withPropertyValues("spring.shell.command.version.show-build-name=true")
				.withPropertyValues("spring.shell.command.version.show-build-time=true")
				.withPropertyValues("spring.shell.command.version.show-build-version=false")
				.withPropertyValues("spring.shell.command.version.show-git-branch=true")
				.withPropertyValues("spring.shell.command.version.show-git-commit-id=true")
				.withPropertyValues("spring.shell.command.version.show-git-short-commit-id=true")
				.withPropertyValues("spring.shell.command.version.show-git-commit-time=true")
				.withUserConfiguration(Config1.class)
				.run((context) -> {
					SpringShellProperties properties = context.getBean(SpringShellProperties.class);
					assertThat(properties.getScript().isEnabled()).isFalse();
					assertThat(properties.getInteractive().isEnabled()).isFalse();
					assertThat(properties.getNoninteractive().isEnabled()).isFalse();
					assertThat(properties.getTheme().getName()).isEqualTo("fake");
					assertThat(properties.getCommand().getClear().isEnabled()).isFalse();
					assertThat(properties.getCommand().getHelp().isEnabled()).isFalse();
					assertThat(properties.getCommand().getHistory().isEnabled()).isFalse();
					assertThat(properties.getCommand().getQuit().isEnabled()).isFalse();
					assertThat(properties.getCommand().getScript().isEnabled()).isFalse();
					assertThat(properties.getCommand().getStacktrace().isEnabled()).isFalse();
					assertThat(properties.getCommand().getCompletion().isEnabled()).isFalse();
					assertThat(properties.getCommand().getCompletion().getRootCommand()).isEqualTo("fake");
					assertThat(properties.getCommand().getVersion().isEnabled()).isFalse();
					assertThat(properties.getCommand().getVersion().getTemplate()).isEqualTo("fake");
					assertThat(properties.getCommand().getVersion().isShowBuildArtifact()).isTrue();
					assertThat(properties.getCommand().getVersion().isShowBuildGroup()).isTrue();
					assertThat(properties.getCommand().getVersion().isShowBuildName()).isTrue();
					assertThat(properties.getCommand().getVersion().isShowBuildTime()).isTrue();
					assertThat(properties.getCommand().getVersion().isShowBuildVersion()).isFalse();
					assertThat(properties.getCommand().getVersion().isShowGitBranch()).isTrue();
					assertThat(properties.getCommand().getVersion().isShowGitCommitId()).isTrue();
					assertThat(properties.getCommand().getVersion().isShowGitShortCommitId()).isTrue();
					assertThat(properties.getCommand().getVersion().isShowGitCommitTime()).isTrue();
				});
	}

	@EnableConfigurationProperties({ SpringShellProperties.class })
	private static class Config1 {
	}
}
