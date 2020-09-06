package pbconverts

import org.scalatest.funsuite.AnyFunSuite
import pbconverts.ConversionTest.TestMessagePB

class ProtoableBuilderSpec extends AnyFunSuite {
  test("test ProtoableBuilder") {
    assertTestMessage(TestMessage.default)
    assertTestMessage(TestMessage.zero)
  }

  def assertTestMessage(testMessage: TestMessage) = {
    val testMessage = TestMessage.default
    val testMessagePB: TestMessagePB = ProtoableBuilder[TestMessage, TestMessagePB]
      .setField(_.getIntValue, _.intValue + 1)
      .build
      .toProto(testMessage)

    assert(testMessagePB.getIntValue == testMessage.intValue + 1)
    val testMessage2 = Scalable[TestMessage, TestMessagePB].toScala(testMessagePB)

    assert(testMessage2.intArray.sameElements(testMessage.intArray))
    assert(testMessage == testMessage2.copy(intValue = testMessage2.intValue - 1, intArray = testMessage.intArray, stringArray = testMessage.stringArray))
  }
}
