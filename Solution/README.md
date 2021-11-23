### Order Failure Metrics

**Branch name:** metrics-prework

**RDE workfows:**
* `rde wflow run metrics-prework-emittingzero-orderprocessortest`

Expected time required: 10 min

You have an order processing class that must perform some tasks to complete an order (check stock, process
payment, verify address and shipping, etc).  Occasionally an order will fail processing.  You want to track
the number of failures and failure rate of your order processor. 

Update the `OrderProcessor` and `MetricsPublisher` to log a metric in CloudWatch with the following information:

1. Namespace: `"EXAMPLE/ORDERS"`
1. Dimension: `"ENVIRONMENT"` with a value of `"PRODUCTION"`
1. Metric Name: `ORDER_FAILURES`
1. Metric Value: 1 if an exception is caught, 0 otherwise.
1. Unit: `StandardUnit.Count`

Note that, like the previous `emittingmetrics` try, we've provided a separate `MetricsPublisher` class that integrates 
with CloudWatch, with the same two methods, `addMetric` and `buildMetricDataRequest`.  

To log the order totals metric, you will:
1. Update `buildMetricDataRequest` in `MetricsPublisher` to build the `PutMetricDataRequest` using the above metric
   information. 
1. Update `processOrder` in `OrderProcessor` to log the order failure count by calling `MetricsPublisher`'s
   `addMetric` method.
