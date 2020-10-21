package sensordata

import akka.stream.scaladsl.RunnableGraph
import cloudflow.akkastream._
import cloudflow.akkastream.scaladsl._
import cloudflow.streamlets.{RoundRobinPartitioner, StreamletShape}
import cloudflow.streamlets.avro._

class SensorDataToMetrics extends AkkaServerStreamlet {
  val in = AvroInlet[SensorData]("in")
  val out = AvroOutlet[Metric]("out").withPartitioner(RoundRobinPartitioner)
  val shape = StreamletShape(in, out)
  def flow =
    FlowWithCommittableContext[SensorData]
      .mapConcat {
        data =>
          List(
            Metric(data.deviceId, data.timestamp, "power", data.measurements.power),
            Metric(data.deviceId, data.timestamp, "rotorSpeed", data.measurements.rotorSpeed),
            Metric(data.deviceId, data.timestamp, "windSpeed", data.measurements.windSpeed)
          )
      }

  override def createLogic(): AkkaStreamletLogic = new RunnableGraphStreamletLogic() {
    override def runnableGraph(): RunnableGraph[_] = sourceWithCommittableContext(in).via(flow).to(committableSink(out))
  }
}
