/*
 * Copyright 2016-2017 Red Hat, Inc, and individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.openshift.booster;

import io.fabric8.kubernetes.api.model.v2_2.Pod;
import io.fabric8.openshift.clnt.v2_2.OpenShiftClient;
import org.arquillian.cube.openshift.impl.requirement.RequiresOpenshift;
import org.arquillian.cube.requirement.ArquillianConditionalRunner;
import org.assertj.core.api.Assertions;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;
import java.util.stream.Collectors;

@RunWith(ArquillianConditionalRunner.class)
@RequiresOpenshift
public class OpenShiftIT {

	@ArquillianResource
	OpenShiftClient client;

	@Test
	public void testRunningPod() throws Exception {
		System.out.println("Test pod is running.");
		System.out.println("OpenShift client version: " + this.client.getClass());
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
