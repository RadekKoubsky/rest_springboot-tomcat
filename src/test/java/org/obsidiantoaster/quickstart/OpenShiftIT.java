/**
 * Copyright 2016-2017 Red Hat, Inc, and individual contributors.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.obsidiantoaster.quickstart;

import io.fabric8.kubernetes.api.model.Pod;
import io.fabric8.kubernetes.client.KubernetesClient;
import org.assertj.core.api.Assertions;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;
import java.util.stream.Collectors;

@RunWith(Arquillian.class)
public class OpenShiftIT {

	@ArquillianResource
	KubernetesClient client;

	@Test
	public void testRunningPod() throws Exception {
		Assertions.assertThat(findPods().iterator()
				.next()
				.getStatus()
				.getPhase())
				.isEqualToIgnoringCase("running");
	}

	private List<Pod> findPods() {
		return this.client.pods()
				.list()
				.getItems()
				.stream()
				.filter(pod -> !pod.getMetadata()
						.getName()
						.contains("build"))
				.collect(Collectors.toList());
	}

}
