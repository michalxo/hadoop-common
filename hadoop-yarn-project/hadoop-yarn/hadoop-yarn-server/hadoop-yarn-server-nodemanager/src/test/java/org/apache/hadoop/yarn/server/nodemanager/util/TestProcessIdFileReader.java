/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.hadoop.yarn.server.nodemanager.util;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import junit.framework.Assert;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.yarn.server.nodemanager.util.ProcessIdFileReader;
import org.junit.Test;

public class TestProcessIdFileReader {

  
  @Test
  public void testNullPath() {
    String pid = null;
    try {
      pid = ProcessIdFileReader.getProcessId(null);
      fail("Expected an error to be thrown for null path");
    } catch (Exception e) {
      // expected
    }
    assert(pid == null);
  }
  
  @Test 
  public void testSimpleGet() throws IOException {
    String rootDir = new File(System.getProperty(
        "test.build.data", "/tmp")).getAbsolutePath();
    File testFile = null;
    
    try {
      testFile = new File(rootDir, "temp.txt");
      PrintWriter fileWriter = new PrintWriter(testFile);
      fileWriter.println("56789");
      fileWriter.close();      
      String processId = null; 
                  
      processId = ProcessIdFileReader.getProcessId(
          new Path(rootDir + Path.SEPARATOR + "temp.txt"));
      Assert.assertEquals("56789", processId);      
      
    } finally {
      if (testFile != null
          && testFile.exists()) {
        testFile.delete();
      }
    }
  }

    
  @Test
  public void testComplexGet() throws IOException {
    String rootDir = new File(System.getProperty(
        "test.build.data", "/tmp")).getAbsolutePath();
    File testFile = null;
    
    try {
      testFile = new File(rootDir, "temp.txt");
      PrintWriter fileWriter = new PrintWriter(testFile);
      fileWriter.println("   ");
      fileWriter.println("");
      fileWriter.println("abc");
      fileWriter.println("-123");
      fileWriter.println("-123 ");
      fileWriter.println(" 23 ");
      fileWriter.println("6236");
      fileWriter.close();      
      String processId = null; 
                  
      processId = ProcessIdFileReader.getProcessId(
          new Path(rootDir + Path.SEPARATOR + "temp.txt"));
      Assert.assertEquals("23", processId);      
      
    } finally {
      if (testFile != null
          && testFile.exists()) {
        testFile.delete();
      }
    }
  }
}
