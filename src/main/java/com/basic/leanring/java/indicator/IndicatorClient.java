package com.basic.leanring.java.indicator;


import java.util.Map;
import java.util.Set;

import com.basic.leanring.java.indicator.impl.IndicatorTransMeta;
import com.basic.leanring.java.indicator.model.CommonCtuEvent;
import com.basic.leanring.java.indicator.model.IndicatorResultRef;
import com.basic.leanring.java.indicator.model.MultiResult;
import com.basic.leanring.java.indicator.model.SingleCalResult;

/**
 * @author sunzihan
 * @version $Id: IndicatorClient.java V 0.1 3/15/17 15:32 sunzihan EXP $
 */
public interface IndicatorClient {

    /**
     * 进行单个指标的计算。
     * <p>
     * 支持
     * <ul>
     * <li>标准指标</li>
     * <li>标准衍生指标</li>
     * <li>泛指标</li>
     * </ul>
     * 三种指标的计算。
     * <p>
     * 如果是计算「标准指标」与「标准衍生指标」，那么不需要指定计算参数。
     * 因为指通过指标名称便能够获取到指标协议，「标准指标」与「标准衍生指标」
     * 的指标协议包含了计算需要的参数。
     * <p>
     * 进行「泛指标」的计算时，需要通过 params 指定泛指标的参数。计算过程中会生成对应的「标准衍生指标」。
     *
     * @param event CTU 通用事件实例
     * @param indicatorName 指标名称
     * @param params 计算泛指标需要的参数列表，可选
     * @return 指标计算结果引用
     * @throws Exception
     */
    public IndicatorResultRef<SingleCalResult> calWithException(CommonCtuEvent event,
                                                                String indicatorName,
                                                                Object... params) throws Exception;

    /**
     * 进行单个指标的计算。
     * <p>
     * 支持
     * <ul>
     * <li>标准指标</li>
     * <li>标准衍生指标</li>
     * <li>泛指标</li>
     * </ul>
     * 三种指标的计算。
     * <p>
     * 如果是计算「标准指标」与「标准衍生指标」，那么不需要指定计算参数。
     * 因为指通过指标名称便能够获取到指标协议，「标准指标」与「标准衍生指标」
     * 的指标协议包含了计算需要的参数。
     * <p>
     * 进行「泛指标」的计算时，需要通过 params 指定泛指标的参数。计算过程中会生成对应的「标准衍生指标」。
     *
     * @param event CTU 通用事件实例
     * @param indicatorName 指标名称
     * @param params 计算泛指标需要的参数列表，可选
     * @return 指标计算结果引用
     * @throws Exception
     */
    public IndicatorResultRef<SingleCalResult> calWithException(Object/*event*/ event,
                                                                String indicatorName,
                                                                Map<String, Object> params)
            throws Exception;

    /**
     * 进行单个指标的计算。
     * <p>
     * 支持
     * <ul>
     * <li>标准指标</li>
     * <li>标准衍生指标</li>
     * <li>泛指标</li>
     * </ul>
     * 三种指标的计算。
     * <p>
     * 如果是计算「标准指标」与「标准衍生指标」，那么不需要指定计算参数。
     * 因为指通过指标名称便能够获取到指标协议，「标准指标」与「标准衍生指标」
     * 的指标协议包含了计算需要的参数。
     * <p>
     * 进行「泛指标」的计算时，需要通过 params 指定泛指标的参数。计算过程中会生成对应的「标准衍生指标」。
     *
     * @param event CTU 通用事件实例
     * @param indicatorName 指标名称
     * @param params 计算泛指标需要的参数列表，可选
     * @return 指标计算结果引用
     * @throws Exception
     */
    public IndicatorResultRef<SingleCalResult> calWithException(Long variableId,CommonCtuEvent event,
                                                                String indicatorName,
                                                                Map<String, Object> params)
            throws Exception;

    /**
     * 批量计算一批指标。
     *
     * @param event CTU 通用事件实例
     * @param indicatorIds 需要计算的指标ID集合
     * @return 指标计算结果引用
     * @throws Exception
     */
    public IndicatorResultRef<MultiResult<Integer>> batchCal(CommonCtuEvent event,
                                                             Set<Integer> indicatorIds)
            throws Exception;

    /**
     * 指标预计算。
     *
     * @param event CTU 通用事件实例
     * @param indicatorIds 需要预计算的指标ID集合
     * @return 指标计算结果引用
     * @throws Exception
     */
    public IndicatorResultRef<MultiResult<Integer>> preCal(CommonCtuEvent event,
                                                           Set<Integer> indicatorIds,
                                                           int velocityTimeout) throws Exception;

    /**
     * 指标批量加载
     *
     * @param event CTU 通用事件实例
     * @param indicatorIds 需要预计算的指标ID集合
     * @return 指标计算结果引用
     * @throws Exception
     */
    public IndicatorResultRef<MultiResult<Integer>> batchLoadByIds(CommonCtuEvent event,
                                                                   Set<Integer> indicatorIds,
                                                                   int velocityTimeout)
            throws Exception;

    /**
     * 指标批量加载
     *
     * @param event CTU 通用事件实例
     * @param indicatorNames 需要预计算的指标名称集合
     * @return 指标计算结果引用
     * @throws Exception
     */
    public IndicatorResultRef<MultiResult<String>> batchLoadByNames(CommonCtuEvent event,
                                                                    Set<String> indicatorNames,
                                                                    int velocityTimeout)
            throws Exception;

    /**
     * 指标批量加载
     * 与batchLoadByNames 不同，该方法计算的指标会参与统计
     * @param event CTU 通用事件实例
     * @param indicatorNames 需要预计算的指标名称集合
     * @return 指标计算结果引用
     * @throws Exception
     *
     */
    public IndicatorResultRef<MultiResult<String>> batchLoadByNamesWithStastics(CommonCtuEvent event,
                                                                                Set<String> indicatorNames,
                                                                                int velocityTimeout)
            throws Exception;

    /**
     * 指标批量加载
     *
     * @param event CTU 通用事件实例
     * @param indicatorIds 需要预计算的指标ID集合
     * @param ioItems io数据点值
     * @return 指标计算结果引用
     * @throws Exception
     */
//    public IndicatorResultRef<MultiResult<Integer>> extBatchLoadByIds(CommonCtuEvent event,
//                                                                      Map<CalculatelDataPoint, Object> ioItems,
//                                                                      Set<Integer> indicatorIds,
//                                                                      int velocityTimeout)
//            throws Exception;

    /**
     * 通过指标名称计算一批指标。
     *
     * @param event CTU 通用事件实例
     * @param indicatorNames 需要计算的指标名称集合
     * @return 指标计算结果引用
     * @throws Exception
     */
    public IndicatorResultRef<MultiResult<String>> batchCalByNames(CommonCtuEvent event,
                                                                   Set<String> indicatorNames)
            throws Exception;

    /**
     * 开启指标计算会话。
     * <p>
     * 接入指标运算模块的系统需要在应用的总入口点（即服务主线程中）上调用该方法，
     * 并且必须保证在调用计算方法之前开启指标计算会话。
     * <p>
     * 该方法的调用与 {@link IndicatorClient#finishSession()} 配对：
     * <pre>
     *  public class ServiceImpl implements Service {
     *
     *      // Container injected...
     *      private IndicatorClient indicatorClient;
     *
     *
     *      public void execute(Object[] args) {
     *          try {
     *              CommonCtuEvent event = getCommonCtuEvent(args);
     *              indicatorClient.startSession(event);
     *
     *              // business code...
     *
     *          } finally {
     *              indicatorClient.finishSession();
     *          }
     *      }
     *  }
     * </pre>
     *
     * @param event 统一 CTU 事件
     */
    public void startSession(CommonCtuEvent event);

    /**
     * 结束指标计算会话，进行扫尾工作，比如缓存清理，存储指标快照等。
     */
    public void finishSession();

    // ------------------------------- 暴露透传处理方法是为了减少集成客户端使用方的理解，降低集成难度。

    /**
     * 若有必要，处理「向下」透传业务。
     *
     * @param transNodeName 透传节点名称
     * @param event 通用事件
     * @param meta 指标客户端元信息
     * @param args 方法调用参数
     */
    public void transDownIfNecessary(String transNodeName, CommonCtuEvent event,
                                     IndicatorTransMeta meta, Object[] args);

    /**
     * 若有必要处理「向上」透传业务。
     *
     * @param transNodeName 透传节点名称
     * @param event 通用事件
     * @param meta 指标客户端元信息
     * @param ret 方法调用返回值
     */
    public void transUpIfNecessary(String transNodeName, CommonCtuEvent event,
                                   IndicatorTransMeta meta, Object ret);

    /**
     * 单独进行指标快照存储。
     *
     * @param event CTU 通用事件
     * @param nodeName 节点名称
     */
    public void storeSnapshotExplicitly(CommonCtuEvent event, String nodeName);

    /**
     * 判断拦截器是否开启。
     *
     * @return 拦截器开启，返回 {@code true} 否则返回 {@code false}
     */
    public boolean isInterceptorOn();
}
