// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: texthandling.proto

package kr.wise.advisor.prepare.textcluster.grpc;

public interface TxtMchOrBuilder extends
    // @@protoc_insertion_point(interface_extends:kr.wise.advisor.prepare.textcluster.TxtMch)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <code>repeated string col_val = 1;</code>
   */
  java.util.List<java.lang.String>
      getColValList();
  /**
   * <code>repeated string col_val = 1;</code>
   */
  int getColValCount();
  /**
   * <code>repeated string col_val = 1;</code>
   */
  java.lang.String getColVal(int index);
  /**
   * <code>repeated string col_val = 1;</code>
   */
  com.google.protobuf.ByteString
      getColValBytes(int index);

  /**
   * <code>float similarity = 2;</code>
   */
  float getSimilarity();
}
