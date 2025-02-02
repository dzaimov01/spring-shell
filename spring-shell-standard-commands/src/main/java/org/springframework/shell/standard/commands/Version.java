/*
 * Copyright 2022 the original author or authors.
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
package org.springframework.shell.standard.commands;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import org.jline.utils.AttributedString;

import org.springframework.boot.info.BuildProperties;
import org.springframework.boot.info.GitProperties;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.shell.standard.AbstractShellComponent;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.style.TemplateExecutor;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.StringUtils;

/**
 * Command to list version and other build related infos.
 *
 * @author Janne Valkealahti
 */
@ShellComponent
public class Version extends AbstractShellComponent implements ResourceLoaderAware {

	/**
	 * Marker interface used in auto-config.
	 */
	public interface Command {
	}

	private BuildProperties buildProperties;
	private GitProperties gitProperties;
	private ResourceLoader resourceLoader;
	private TemplateExecutor templateExecutor;
	private String template;
	private boolean showBuildGroup;
	private boolean showBuildArtifact;
	private boolean showBuildName;
	private boolean showBuildVersion;
	private boolean showBuildTime;
	private boolean showGitBranch;
	private boolean showGitCommitId;
	private boolean showGitShortCommitId;
	private boolean showGitCommitTime;

	public Version(TemplateExecutor templateExecutor) {
		this.templateExecutor = templateExecutor;
	}

	@Override
	public void setResourceLoader(ResourceLoader resourceLoader) {
		this.resourceLoader = resourceLoader;
	}

	@ShellMethod(key = "version", value = "Show version info")
	public AttributedString version() {
		String templateResource = resourceAsString(resourceLoader.getResource(template));

		Map<String, Object> attributes = new HashMap<>();
		if (buildProperties != null) {
			if (showBuildGroup && StringUtils.hasText(buildProperties.getGroup())) {
				attributes.put("buildGroup", buildProperties.getGroup());
			}
			if (showBuildArtifact && StringUtils.hasText(buildProperties.getArtifact())) {
				attributes.put("buildArtifact", buildProperties.getArtifact());
			}
			if (showBuildName && StringUtils.hasText(buildProperties.getName())) {
				attributes.put("buildName", buildProperties.getName());
			}
			if (showBuildVersion && StringUtils.hasText(buildProperties.getVersion())) {
				attributes.put("buildVersion", buildProperties.getVersion());
			}
			if (showBuildTime && buildProperties.getTime() != null) {
				attributes.put("buildTime", buildProperties.getTime().toString());
			}
		}
		if (gitProperties != null) {
			if (showGitBranch && StringUtils.hasText(gitProperties.getBranch())) {
				attributes.put("gitBranch", gitProperties.getBranch());
			}
			if (showGitCommitId && StringUtils.hasText(gitProperties.getCommitId())) {
				attributes.put("gitCommitId", gitProperties.getCommitId());
			}
			if (showGitShortCommitId && StringUtils.hasText(gitProperties.getShortCommitId())) {
				attributes.put("gitShortCommitId", gitProperties.getShortCommitId());
			}
			if (showGitCommitTime && gitProperties.getCommitTime() != null) {
				attributes.put("gitCommitTime", gitProperties.getCommitTime().toString());
			}
		}
		AttributedString rendered = templateExecutor.render(templateResource, attributes);
		return rendered;
	}

	public void setBuildProperties(BuildProperties buildProperties) {
		this.buildProperties = buildProperties;
	}

	public void setGitProperties(GitProperties gitProperties) {
		this.gitProperties = gitProperties;
	}

	public void setTemplate(String template) {
		this.template = template;
	}

	public void setShowBuildGroup(boolean showBuildGroup) {
		this.showBuildGroup = showBuildGroup;
	}

	public void setShowBuildArtifact(boolean showBuildArtifact) {
		this.showBuildArtifact = showBuildArtifact;
	}

	public void setShowBuildName(boolean showBuildName) {
		this.showBuildName = showBuildName;
	}

	public void setShowBuildVersion(boolean showBuildVersion) {
		this.showBuildVersion = showBuildVersion;
	}

	public void setShowBuildTime(boolean showBuildTime) {
		this.showBuildTime = showBuildTime;
	}

	public void setShowGitBranch(boolean showGitBranch) {
		this.showGitBranch = showGitBranch;
	}

	public void setShowGitCommitId(boolean showGitCommitId) {
		this.showGitCommitId = showGitCommitId;
	}

	public void setShowGitShortCommitId(boolean showGitShortCommitId) {
		this.showGitShortCommitId = showGitShortCommitId;
	}

	public void setShowGitCommitTime(boolean showGitCommitTime) {
		this.showGitCommitTime = showGitCommitTime;
	}

	private static String resourceAsString(Resource resource) {
		try (Reader reader = new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8)) {
			return FileCopyUtils.copyToString(reader);
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}
}
