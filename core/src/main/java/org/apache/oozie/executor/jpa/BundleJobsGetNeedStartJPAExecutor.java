/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.oozie.executor.jpa;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.oozie.BundleJobBean;
import org.apache.oozie.ErrorCode;

/**
 * Get a list of bundle Jobs that need to be started;
 */
public class BundleJobsGetNeedStartJPAExecutor implements JPAExecutor<List<BundleJobBean>> {
    private Date date;

    public BundleJobsGetNeedStartJPAExecutor(Date d) {
        this.date = d;
    }

    @Override
    public String getName() {
        return "BundleJobsGetNeedStartJPAExecutor";
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<BundleJobBean> execute(EntityManager em) throws JPAExecutorException {
        List<BundleJobBean> bjBeans;
        try {
            Query q = em.createNamedQuery("GET_BUNDLE_JOBS_NEED_START");
            q.setParameter("currentTime", new Timestamp(date.getTime()));
            bjBeans = q.getResultList();

        }
        catch (Exception e) {
            throw new JPAExecutorException(ErrorCode.E0603, e.getMessage(), e);
        }
        return bjBeans;
    }
}
