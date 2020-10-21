package sensordata

import akka.stream.scaladsl.RunnableGraph
import cloudflow.akkastream._
import cloudflow.akkastream.scaladsl._
import cloudflow.akkastream.util.scaladsl._
import cloudflow.streamlets._
import cloudflow.streamlets.avro._

class MetricsValidation extends AkkaServerStreamlet {
  val in = AvroInlet[Metric]("in")
  val invalid = AvroOutlet[InvalidMetric]("invalid").withPartitioner(metric => metric.metric.deviceId.toString)
  val valid = AvroOutlet[Metric]("valid").withPartitioner(RoundRobinPartitioner)
  def shape = StreamletShape(in).withOutlets(invalid, valid)

  override protected def createLogic(): AkkaStreamletLogic = new RunnableGraphStreamletLogic() {
    override def runnableGraph = sourceWithCommittableContext(in).to(Splitter.sink(flow, invalid, valid))
    def flow =
      FlowWithCommittableContext[Metric]
        .map { metric ⇒
          if (!SensorDataUtils.isValidMetric(metric)) Left(InvalidMetric(metric, "All measurements must be positive numbers!"))
          else Right(metric)
        }
  }
}
