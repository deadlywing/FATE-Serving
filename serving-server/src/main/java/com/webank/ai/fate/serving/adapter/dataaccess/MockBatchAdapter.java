/*
 * Copyright 2019 The FATE Authors. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.webank.ai.fate.serving.adapter.dataaccess;


import com.webank.ai.fate.serving.core.bean.BatchHostFeatureAdaptorResult;
import com.webank.ai.fate.serving.core.bean.BatchHostFederatedParams;
import com.webank.ai.fate.serving.core.bean.Context;
import com.webank.ai.fate.serving.core.constant.StatusCode;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MockBatchAdapter implements BatchFeatureDataAdaptor {
    private static final Logger logger = LoggerFactory.getLogger(MockBatchAdapter.class);

//    @Override
//    public ReturnResult getData(Context context, Map<String, Object> featureIds) {
//        ReturnResult returnResult = new ReturnResult();
//        Map<String, Object> data = new HashMap<>();
//        try {
//            String mockData = "x0:1.88669,x1:-1.359293,x2:2.303601,x3:2.001237,x4:1.307686,x5:2.616665,x6:2.109526,x7:2.296076,x8:2.750622,x9:1.937015";
//            for (String kv : StringUtils.split(mockData, ",")) {
//                String[] a = StringUtils.split(kv, ":");
//                data.put(a[0], Double.valueOf(a[1]));
//            }9
//            returnResult.setData(data);
//            returnResult.setRetcode(InferenceRetCode.OK);
//
//            if (logger.isDebugEnabled()) {
//                logger.debug("MockAdapter result, {}", JSONObject.toJSONString(returnResult));
//            }
//        } catch (Exception ex) {
//            logger.error(ex.getMessage());
//            returnResult.setRetcode(StatusCode.SYSTEM_ERROR);
//        }
//        return returnResult;
//    }

    @Override
    public BatchHostFeatureAdaptorResult getFeatures(Context context, List<BatchHostFederatedParams.SingleInferenceData> featureIdList) {

        BatchHostFeatureAdaptorResult batchHostFeatureAdaptorResult = new BatchHostFeatureAdaptorResult();

        featureIdList.forEach(singleInferenceData -> {
            Map<Integer, BatchHostFeatureAdaptorResult.SingleBatchHostFeatureAdaptorResult> indexMap = batchHostFeatureAdaptorResult.getIndexResultMap();
            Map<String, Object> data = new HashMap<>();
            String mockData = "x0:1.88669,x1:-1.359293,x2:2.303601,x3:2.001237,x4:1.307686,x5:2.616665,x6:2.109526,x7:2.296076,x8:2.750622,x9:1.937015";
            for (String kv : StringUtils.split(mockData, ",")) {
                String[] a = StringUtils.split(kv, ":");
                data.put(a[0], Double.valueOf(a[1]));
            }
            BatchHostFeatureAdaptorResult.SingleBatchHostFeatureAdaptorResult singleBatchHostFeatureAdaptorResult = new BatchHostFeatureAdaptorResult.SingleBatchHostFeatureAdaptorResult();
            singleBatchHostFeatureAdaptorResult.setFeatures(data);
            singleBatchHostFeatureAdaptorResult.setRetcode(StatusCode.SUCCESS);
            indexMap.put(singleInferenceData.getIndex(), singleBatchHostFeatureAdaptorResult);

        });
        batchHostFeatureAdaptorResult.setRetcode(StatusCode.SUCCESS);

        return batchHostFeatureAdaptorResult;
    }

    @Override
    public List<ParamDescriptor> desc() {
        return null;
    }
}