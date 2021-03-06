package org.hetec.binaryNumber;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
    BinaryNumberGenerationTest.class,
    BinaryNumberMathOperationsTest.class,
    BinaryNumberOutputTest.class,
    BinaryNumberGenericOperationsTest.class,
    BinaryNumberFactoryTest.class})
public class BinaryNumberTestSuite {}
