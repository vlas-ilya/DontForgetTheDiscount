package su.tease.project.core.mvi.impl.stub.stub2

fun StubState2.toVO() =
    StubState2VO(
        intValue = intValue,
        stringValue = stringValue,
        listStringValue = listStringValue,
    )
