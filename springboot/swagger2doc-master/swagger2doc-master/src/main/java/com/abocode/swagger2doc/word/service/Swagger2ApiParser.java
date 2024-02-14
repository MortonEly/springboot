package com.abocode.swagger2doc.word.service;

import com.abocode.swagger2doc.utils.JsonUtils;
import com.abocode.swagger2doc.word.model.Attribute;
import com.abocode.swagger2doc.word.model.Request;
import com.abocode.swagger2doc.word.model.Response;
import com.abocode.swagger2doc.word.model.Table;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import java.util.*;

@Service
@Slf4j
public class Swagger2ApiParser {
    public static final String SCHEMA = "schema";
    public static final String ITEMS = "items";
    public static final String CONSUMES = "consumes";
    public static final String PRODUCES = "produces";
    public static final String FORMAT = "format";
    public static final String DESCRIPTION = "description";
    public static final String ARRAY = "array";
    public static final String OBJECT = "object";
    public static final String DEFINITIONS = "#/definitions/";
    public static final String PARAMETERS = "parameters";

    // 处理方案一： 同一路由下所有请求方式合并为一个表格
    public List<Table> getResult(Map<String, Object> map) {
        //解析model
        Map<String, Map<String, Object>> definitions = (Map<String, Map<String, Object>>) map.get("definitions");
        Map<String, Attribute> definitionMap = parseDefinitions(definitions);
        //解析paths
        Map<String, Map<String, Object>> paths = (Map<String, Map<String, Object>>) map.get("paths");
        return buildDefinitionsAndProcessByPath(paths, definitionMap);
    }

    //  处理方案二： 新增： 同一路由下所有请求方式单独为一个表格
    private List<Table> getResultGroupByPath(Map<String, Object> map) {
        //解析model
        Map<String, Map<String, Object>> definitions = (Map<String, Map<String, Object>>) map.get("definitions");
        Map<String, Attribute> definitionMap = parseDefinitions(definitions);

        //解析paths
        Map<String, Map<String, Object>> paths = (Map<String, Map<String, Object>>) map.get("paths");
        //获取全局请求参数格式作为默认请求参数格式
        List<String> defaultConsumes = (List<String>) map.get(CONSUMES);

        //获取全局响应参数格式作为默认响应参数格式
        List<String> defaultProduces = (List<String>) map.get(PRODUCES);
        return buildDefinitionsAndProcessByGroupPath(paths, definitionMap, defaultConsumes, defaultProduces);
    }

    private List<Table> buildDefinitionsAndProcessByGroupPath(Map<String, Map<String, Object>> paths, Map<String, Attribute> definitionMap, List<String> defaultConsumes, List<String> defaultProduces) {
        List<Table> results = new ArrayList<>();
        if (paths != null) {
            Iterator<Map.Entry<String, Map<String, Object>>> it = paths.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<String, Map<String, Object>> path = it.next();

                // 0. 获取该路由下所有请求方式的公共参数
                Map<String, Object> methods = path.getValue();
                List<Map<String, Object>> commonParameters = (List<Map<String, Object>>) methods.get(PARAMETERS);

                Iterator<Map.Entry<String, Object>> it2 = path.getValue().entrySet().iterator();
                // 1.请求路径
                String url = path.getKey();

                while (it2.hasNext()) {
                    Map.Entry<String, Object> request = it2.next();
                    // 2.请求方式，类似为 get,post,delete,put 这样
                    String requestType = request.getKey();
                    if (PARAMETERS.equals(requestType)) {
                        continue;
                    }
                    Table table = buildGroupTable(url, definitionMap, request, commonParameters, defaultConsumes, defaultProduces);
                    results.add(table);
                }
            }
        }
        return results;
    }

    private Table buildGroupTable(String url, Map<String, Attribute> definitionMap, Map.Entry<String, Object> request, List<Map<String, Object>> commonParameters, List<String> defaultConsumes, List<String> defaultProduces) {
        Map<String, Object> content = (Map<String, Object>) request.getValue();
        // 2.请求方式，类似为 get,post,delete,put 这样
        String requestType = request.getKey();
        // 4. 大标题（类说明）
        String title = String.valueOf(((List) content.get("tags")).get(0));

        // 5.小标题 （方法说明）
        String tag = String.valueOf(content.get("operationId"));

        // 6.接口描述
        String description = String.valueOf(content.get(DESCRIPTION));

        // 7.请求参数格式，类似于 multipart/form-data
        String requestForm = "";
        List<String> consumes = (List) content.get(CONSUMES);
        if (!CollectionUtils.isEmpty(consumes)) {
            requestForm = StringUtils.join(consumes, ",");
        } else {
            requestForm = StringUtils.join(defaultConsumes, ",");
        }

        // 8.返回参数格式，类似于 application/json
        String responseForm = "";
        List<String> produces = (List) content.get(PRODUCES);
        if (!CollectionUtils.isEmpty(produces)) {
            responseForm = StringUtils.join(produces, ",");
        } else {
            responseForm = StringUtils.join(defaultProduces, ",");
        }

        // 9. 请求体
        List<Map<String, Object>> parameters = (ArrayList) content.get(PARAMETERS);

        if (!CollectionUtils.isEmpty(parameters)) {
            if (commonParameters != null) {
                parameters.addAll(commonParameters);
            }
        } else {
            if (commonParameters != null) {
                parameters = commonParameters;
            }
        }

        // 10.返回体
        Map<String, Object> responses = (LinkedHashMap) content.get("responses");
        //封装Table
        Table table = new Table();

        table.setTitle(title);
        table.setUrl(url);
        table.setTag(tag);
        table.setDescription(description);
        table.setRequestForm(requestForm);
        table.setResponseForm(responseForm);
        table.setRequestType(requestType);
        table.setRequestList(processRequestList(parameters, definitionMap));
        table.setResponseList(processResponseCodeList(responses));

        // 取出来状态是200时的返回值
        Map<String, Object> obj = (Map<String, Object>) responses.get("200");
        if (obj != null && obj.get(SCHEMA) != null) {
            table.setAttribute(processResponseModelAttrs(obj, definitionMap));
        }

        //示例
        table.setRequestParam(processRequestParam(table.getRequestList()));
        table.setResponseParam(processResponseParam(obj, definitionMap));
        return table;
    }

    private List<Table> buildDefinitionsAndProcessByPath(Map<String, Map<String, Object>> paths, Map<String, Attribute> definitionMap) {
        List<Table> results = new ArrayList<>();
        if (paths != null) {
            Iterator<Map.Entry<String, Map<String, Object>>> it = paths.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<String, Map<String, Object>> path = it.next();

                Iterator<Map.Entry<String, Object>> it2 = path.getValue().entrySet().iterator();
                // 1.请求路径
                String url = path.getKey();

                // 2. 循环解析每个子节点，适应同一个路径几种请求方式的场景
                while (it2.hasNext()) {
                    Map.Entry<String, Object> request = it2.next();
                    Table table = buildTable(url, definitionMap, request);
                    results.add(table);
                }
            }
        }
        return results;
    }

    private Table buildTable(String url, Map<String, Attribute> definitionMap, Map.Entry<String, Object> request) {
        // 2. 请求方式，类似为 get,post,delete,put 这样
        String requestType = request.getKey();

        Map<String, Object> content = (Map<String, Object>) request.getValue();
        // 4. 大标题（类说明）
        String title = String.valueOf(((List) content.get("tags")).get(0));

        // 5.小标题 （方法说明）
        String tag = String.valueOf(content.get("summary"));

        // 6.接口描述
        String description = String.valueOf(content.get("summary"));

        // 7.请求参数格式，类似于 multipart/form-data
        String requestForm = "";
        List<String> consumes = (List) content.get(CONSUMES);
        if (!CollectionUtils.isEmpty(consumes)) {
            requestForm = StringUtils.join(consumes, ",");
        }

        // 8.返回参数格式，类似于 application/json
        String responseForm = "";
        List<String> produces = (List) content.get(PRODUCES);
        if (!CollectionUtils.isEmpty(produces)) {
            responseForm = StringUtils.join(produces, ",");
        }

        // 9. 请求体
        List<Map<String, Object>> parameters = (ArrayList) content.get(PARAMETERS);

        // 10.返回体
        Map<String, Object> responses = (LinkedHashMap) content.get("responses");

        //封装Table
        Table table = new Table();

        table.setTitle(title);
        table.setUrl(url);
        table.setTag(tag);
        table.setDescription(description);
        table.setRequestForm(requestForm);
        table.setResponseForm(responseForm);
        table.setRequestType(requestType);
        table.setRequestList(processRequestList(parameters, definitionMap));
        table.setResponseList(processResponseCodeList(responses));

        // 取出来状态是200时的返回值
        Map<String, Object> obj = (Map<String, Object>) responses.get("200");
        if (obj != null && obj.get(SCHEMA) != null) {
            table.setAttribute(processResponseModelAttrs(obj, definitionMap));
        }
        //示例
        table.setRequestParam(processRequestParam(table.getRequestList()));
        table.setResponseParam(processResponseParam(obj, definitionMap));
        return table;
    }


    /**
     * 处理请求参数列表
     *
     * @param parameters
     * @param definitionMap
     * @return
     */
    private List<Request> processRequestList(List<Map<String, Object>> parameters, Map<String, Attribute> definitionMap) {
        List<Request> requestList = new ArrayList<>();
        if (CollectionUtils.isEmpty(parameters)) {
            return requestList;
        }
        for (Map<String, Object> param : parameters) {
            Request request = buildRequest(param, definitionMap);
            requestList.add(request);
        }
        return requestList;
    }

    private Request buildRequest(Map<String, Object> param, Map<String, Attribute> definitionMap) {
        Object in = param.get("in");
        Request request = new Request();
        request.setName(String.valueOf(param.get("name")));
        request.setType(param.get("type") == null ? OBJECT : param.get("type").toString());
        Object fa = param.get(FORMAT);
        if (fa != null) {
            request.setType(request.getType() + "(" + fa + ")");
        }
        request.setParamType(String.valueOf(in));
        // 考虑对象参数类型
        if (in != null && "body".equals(in)) {
            Map<String, Object> schema = (Map) param.get(SCHEMA);
            Object ref = schema.get("$ref");
            // 数组情况另外处理
            if (schema.get("type") != null && ARRAY.equals(schema.get("type"))) {
                ref = ((Map) schema.get(ITEMS)).get("$ref");
                request.setType(ARRAY);
            }
            if (ref != null) {
                request.setType(request.getType() + ":" + ref.toString().replace(DEFINITIONS, ""));
                request.setAttribute(definitionMap.get(ref));
            }
        }
        // 是否必填
        request.setRequire(false);
        if (param.get("required") != null) {
            request.setRequire((Boolean) param.get("required"));
        }
        // 参数说明
        request.setRemark(String.valueOf(param.get(DESCRIPTION)));
        return request;
    }


    /**
     * 处理返回码列表
     *
     * @param responses 全部状态码返回对象
     * @return
     */
    private List<Response> processResponseCodeList(Map<String, Object> responses) {
        List<Response> responseList = new ArrayList<>();
        Iterator<Map.Entry<String, Object>> resIt = responses.entrySet().iterator();
        while (resIt.hasNext()) {
            Map.Entry<String, Object> entry = resIt.next();
            Response response = new Response();
            // 状态码 200 201 401 403 404 这样
            response.setName(entry.getKey());
            LinkedHashMap<String, Object> statusCodeInfo = (LinkedHashMap) entry.getValue();
            response.setDescription(String.valueOf(statusCodeInfo.get(DESCRIPTION)));
            Object schema = statusCodeInfo.get(SCHEMA);
            if (schema != null) {
                Object originalRef = ((LinkedHashMap) schema).get("originalRef");
                response.setRemark(originalRef == null ? "" : originalRef.toString());
            }
            responseList.add(response);
        }
        return responseList;
    }

    /**
     * 处理返回属性列表
     *
     * @param responseObj
     * @param definitionMap
     * @return
     */
    private Attribute processResponseModelAttrs(Map<String, Object> responseObj, Map<String, Attribute> definitionMap) {
        Map<String, Object> schema = (Map<String, Object>) responseObj.get(SCHEMA);
        String type = (String) schema.get("type");
        String ref = null;
        //数组
        if (ARRAY.equals(type)) {
            Map<String, Object> items = (Map<String, Object>) schema.get(ITEMS);
            if (items != null && items.get("$ref") != null) {
                ref = (String) items.get("$ref");
            }
        }
        //对象
        if (schema.get("$ref") != null) {
            ref = (String) schema.get("$ref");
        }

        //其他类型
        Attribute attribute = new Attribute();
        attribute.setType(StringUtils.defaultIfBlank(type, StringUtils.EMPTY));

        if (StringUtils.isNotBlank(ref) && definitionMap.get(ref) != null) {
            attribute = definitionMap.get(ref);
        }
        return attribute;
    }

    /**
     * 解析Definition
     *
     * @param definitions
     * @return
     */
    private Map<String, Attribute> parseDefinitions(Map<String, Map<String, Object>> definitions) {

        Map<String, Attribute> definitionMap = new HashMap<>(256);
        if (definitions != null) {
            Iterator<String> modelNameIt = definitions.keySet().iterator();
            while (modelNameIt.hasNext()) {
                String modeName = modelNameIt.next();
                getAndPutModelAttr(definitions, definitionMap, modeName);
            }
        }
        return definitionMap;
    }

    /**
     * 递归生成ModelAttr
     * 对$ref类型设置具体属性
     */
    private Attribute getAndPutModelAttr(Map<String, Map<String, Object>> swaggerMap, Map<String, Attribute> resMap, String modeName) {
        Attribute modeAttr;
        if ((modeAttr = resMap.get(DEFINITIONS + modeName)) == null) {
            modeAttr = new Attribute();
            resMap.put(DEFINITIONS + modeName, modeAttr);
        } else if (modeAttr.isCompleted()) {
            return resMap.get(DEFINITIONS + modeName);
        }
        Map<String, Object> modeProperties = (Map<String, Object>) swaggerMap.get(modeName).get("properties");
        if (modeProperties == null) {
            return null;
        }
        Iterator<Map.Entry<String, Object>> mIt = modeProperties.entrySet().iterator();

        List<Attribute> attrList = new ArrayList<>();
        //解析属性
        while (mIt.hasNext()) {
            Map.Entry<String, Object> mEntry = mIt.next();
            Map<String, Object> attrInfoMap = (Map<String, Object>) mEntry.getValue();
            Attribute child = new Attribute();
            child.setName(mEntry.getKey());
            child.setType((String) attrInfoMap.get("type"));
            if (attrInfoMap.get(FORMAT) != null) {
                child.setType(child.getType() + "(" + attrInfoMap.get(FORMAT) + ")");
            }
            child.setType(StringUtils.defaultIfBlank(child.getType(), OBJECT));

            Object ref = attrInfoMap.get("$ref");
            Object items = attrInfoMap.get(ITEMS);
            if (ref != null || (items != null && (ref = ((Map) items).get("$ref")) != null)) {
                String refName = ref.toString();
                //截取 #/definitions/ 后面的
                String clsName = refName.substring(14);
                modeAttr.setCompleted(true);
                Attribute refModel = getAndPutModelAttr(swaggerMap, resMap, clsName);
                if (refModel != null) {
                    child.setProperties(refModel.getProperties());
                }
                child.setType(child.getType() + ":" + clsName);
            }
            child.setDescription((String) attrInfoMap.get(DESCRIPTION));
            attrList.add(child);
        }
        Object title = swaggerMap.get(modeName).get("title");
        Object description = swaggerMap.get(modeName).get(DESCRIPTION);
        modeAttr.setClassName(title == null ? "" : title.toString());
        modeAttr.setDescription(description == null ? "" : description.toString());
        modeAttr.setProperties(attrList);
        return modeAttr;
    }

    /**
     * 处理返回值
     *
     * @param responseObj
     * @return
     */
    private String processResponseParam(Map<String, Object> responseObj, Map<String, Attribute> definitionMap) {
        if (responseObj != null && responseObj.get(SCHEMA) != null) {
            Map<String, Object> schema = (Map<String, Object>) responseObj.get(SCHEMA);
            String type = (String) schema.get("type");
            String ref = null;
            // 数组
            if (ARRAY.equals(type)) {
                Map<String, Object> items = (Map<String, Object>) schema.get(ITEMS);
                if (items != null && items.get("$ref") != null) {
                    ref = (String) items.get("$ref");
                }
            }
            // 对象
            if (schema.get("$ref") != null) {
                ref = (String) schema.get("$ref");
            }
            if (StringUtils.isNotEmpty(ref)) {
                return build(ref, definitionMap);
            }
        }
        return StringUtils.EMPTY;
    }

    private String build(String ref, Map<String, Attribute> definitionMap) {
        Attribute attribute = definitionMap.get(ref);
        if (attribute != null && !CollectionUtils.isEmpty(attribute.getProperties())) {
            Map<String, Object> responseMap = new HashMap<>(8);
            for (Attribute subAttribute : attribute.getProperties()) {
                responseMap.put(subAttribute.getName(), getValue(subAttribute.getType(), subAttribute));
            }
            return JsonUtils.writeJsonStr(responseMap);
        }
        return null;
    }

    /**
     * 封装请求体
     *
     * @param list
     * @return
     */
    private String processRequestParam(List<Request> list) {
        Map<String, Object> headerMap = new LinkedHashMap<>();
        Map<String, Object> queryMap = new LinkedHashMap<>();
        Map<String, Object> jsonMap = new LinkedHashMap<>();
        if (!CollectionUtils.isEmpty(list)) {
            for (Request request : list) {
                String name = request.getName();
                String paramType = request.getParamType();
                Object value = getValue(request.getType(), request.getAttribute());
                switch (paramType) {
                    case "header":
                        headerMap.put(name, value);
                        break;
                    case "query":
                        queryMap.put(name, value);
                        break;
                    case "body":
                        jsonMap.put(name, value);
                        break;
                    default:
                        break;

                }
            }
        }
        StringBuilder res = new StringBuilder();
        if (!queryMap.isEmpty()) {
            res.append(getUrlParamsByMap(queryMap));
        }
        if (!headerMap.isEmpty()) {
            res.append(" " + getHeaderByMap(headerMap));
        }
        if (!jsonMap.isEmpty()) {
            if (jsonMap.size() == 1) {
                for (Map.Entry<String, Object> entry : jsonMap.entrySet()) {
                    res.append(" -d '" + JsonUtils.writeJsonStr(entry.getValue()) + "'");
                }
            } else {
                res.append(" -d '" + JsonUtils.writeJsonStr(jsonMap) + "'");
            }
        }
        return res.toString();
    }

    /**
     * 例子中，字段的默认值
     *
     * @param type      类型
     * @param attribute 引用的类型
     * @return
     */
    private Object getValue(String type, Attribute attribute) {
        int pos;
        if ((pos = type.indexOf(":")) != -1) {
            type = type.substring(0, pos);
        }
        switch (type) {
            case "string":
                return "string";
            case "string(date-time)":
                return "2020/01/01 00:00:00";
            case "integer":
            case "integer(int64)":
            case "integer(int32)":
                return 0;
            case "number":
                return 0.0;
            case "boolean":
                return true;
            case "file":
                return "(binary)";
            case ARRAY:
                List<Map<String, Object>> list = new ArrayList<>();
                Map<String, Object> map = new LinkedHashMap<>();
                if (attribute != null && !CollectionUtils.isEmpty(attribute.getProperties())) {
                    for (Attribute subAttribute : attribute.getProperties()) {
                        map.put(subAttribute.getName(), getValue(subAttribute.getType(), subAttribute));
                    }
                }
                list.add(map);
                return list;
            case OBJECT:
                map = new LinkedHashMap<>();
                if (attribute != null && !CollectionUtils.isEmpty(attribute.getProperties())) {
                    for (Attribute subAttribute : attribute.getProperties()) {
                        map.put(subAttribute.getName(), getValue(subAttribute.getType(), subAttribute));
                    }
                }
                return map;
            default:
                return null;
        }
    }

    /**
     * 将map转换成url
     */
    public static String getUrlParamsByMap(Map<String, Object> map) {
        if (map == null || map.isEmpty()) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            sb.append(entry.getKey() + "=" + entry.getValue());
            sb.append("&");
        }
        String s = sb.toString();
        if (s.endsWith("&")) {
            s = StringUtils.substringBeforeLast(s, "&");
        }
        return s;
    }

    /**
     * 将map转换成header
     */
    public static String getHeaderByMap(Map<String, Object> map) {
        if (map == null || map.isEmpty()) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            sb.append("--header '");
            sb.append(entry.getKey()).append(":").append(entry.getValue());
            sb.append("'");
        }
        return sb.toString();
    }
}
