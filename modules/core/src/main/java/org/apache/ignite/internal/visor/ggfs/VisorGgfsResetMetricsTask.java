/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.ignite.internal.visor.ggfs;

import org.apache.ignite.*;
import org.apache.ignite.internal.processors.task.*;
import org.apache.ignite.internal.util.typedef.internal.*;
import org.apache.ignite.internal.visor.*;

import java.util.*;

/**
 * Resets GGFS metrics.
 */
@GridInternal
public class VisorGgfsResetMetricsTask extends VisorOneNodeTask<Set<String>, Void> {
    /** */
    private static final long serialVersionUID = 0L;

    /** {@inheritDoc} */
    @Override protected VisorGgfsResetMetricsJob job(Set<String> arg) {
        return new VisorGgfsResetMetricsJob(arg, debug);
    }

    /**
     * Job that reset GGFS metrics.
     */
    private static class VisorGgfsResetMetricsJob extends VisorJob<Set<String>, Void> {
        /** */
        private static final long serialVersionUID = 0L;

        /**
         * @param arg GGFS names.
         * @param debug Debug flag.
         */
        private VisorGgfsResetMetricsJob(Set<String> arg, boolean debug) {
            super(arg, debug);
        }

        /** {@inheritDoc} */
        @Override protected Void run(Set<String> ggfsNames) {
            for (String ggfsName: ggfsNames)
                try {
                    ignite.fileSystem(ggfsName).resetMetrics();
                }
                catch (IllegalArgumentException iae) {
                    throw new IgniteException("Failed to reset metrics for GGFS: " + ggfsName, iae);
                }

            return null;
        }

        /** {@inheritDoc} */
        @Override public String toString() {
            return S.toString(VisorGgfsResetMetricsJob.class, this);
        }
    }
}
