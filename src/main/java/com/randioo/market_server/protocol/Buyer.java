// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: Buyer.proto

package com.randioo.market_server.protocol;

public final class Buyer {
  private Buyer() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
  }
  public static final class BuyerRequest extends
      com.google.protobuf.GeneratedMessage {
    // Use BuyerRequest.newBuilder() to construct.
    private BuyerRequest() {
      initFields();
    }
    private BuyerRequest(boolean noInit) {}
    
    private static final BuyerRequest defaultInstance;
    public static BuyerRequest getDefaultInstance() {
      return defaultInstance;
    }
    
    public BuyerRequest getDefaultInstanceForType() {
      return defaultInstance;
    }
    
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return com.randioo.market_server.protocol.Buyer.internal_static_com_randioo_market_server_protocol_BuyerRequest_descriptor;
    }
    
    protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return com.randioo.market_server.protocol.Buyer.internal_static_com_randioo_market_server_protocol_BuyerRequest_fieldAccessorTable;
    }
    
    // optional .com.randioo.market_server.protocol.BuyerData buyerData = 1;
    public static final int BUYERDATA_FIELD_NUMBER = 1;
    private boolean hasBuyerData;
    private com.randioo.market_server.protocol.Entity.BuyerData buyerData_;
    public boolean hasBuyerData() { return hasBuyerData; }
    public com.randioo.market_server.protocol.Entity.BuyerData getBuyerData() { return buyerData_; }
    
    private void initFields() {
      buyerData_ = com.randioo.market_server.protocol.Entity.BuyerData.getDefaultInstance();
    }
    public final boolean isInitialized() {
      return true;
    }
    
    public void writeTo(com.google.protobuf.CodedOutputStream output)
                        throws java.io.IOException {
      getSerializedSize();
      if (hasBuyerData()) {
        output.writeMessage(1, getBuyerData());
      }
      getUnknownFields().writeTo(output);
    }
    
    private int memoizedSerializedSize = -1;
    public int getSerializedSize() {
      int size = memoizedSerializedSize;
      if (size != -1) return size;
    
      size = 0;
      if (hasBuyerData()) {
        size += com.google.protobuf.CodedOutputStream
          .computeMessageSize(1, getBuyerData());
      }
      size += getUnknownFields().getSerializedSize();
      memoizedSerializedSize = size;
      return size;
    }
    
    public static com.randioo.market_server.protocol.Buyer.BuyerRequest parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data).buildParsed();
    }
    public static com.randioo.market_server.protocol.Buyer.BuyerRequest parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data, extensionRegistry)
               .buildParsed();
    }
    public static com.randioo.market_server.protocol.Buyer.BuyerRequest parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data).buildParsed();
    }
    public static com.randioo.market_server.protocol.Buyer.BuyerRequest parseFrom(
        byte[] data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data, extensionRegistry)
               .buildParsed();
    }
    public static com.randioo.market_server.protocol.Buyer.BuyerRequest parseFrom(java.io.InputStream input)
        throws java.io.IOException {
      return newBuilder().mergeFrom(input).buildParsed();
    }
    public static com.randioo.market_server.protocol.Buyer.BuyerRequest parseFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return newBuilder().mergeFrom(input, extensionRegistry)
               .buildParsed();
    }
    public static com.randioo.market_server.protocol.Buyer.BuyerRequest parseDelimitedFrom(java.io.InputStream input)
        throws java.io.IOException {
      Builder builder = newBuilder();
      if (builder.mergeDelimitedFrom(input)) {
        return builder.buildParsed();
      } else {
        return null;
      }
    }
    public static com.randioo.market_server.protocol.Buyer.BuyerRequest parseDelimitedFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      Builder builder = newBuilder();
      if (builder.mergeDelimitedFrom(input, extensionRegistry)) {
        return builder.buildParsed();
      } else {
        return null;
      }
    }
    public static com.randioo.market_server.protocol.Buyer.BuyerRequest parseFrom(
        com.google.protobuf.CodedInputStream input)
        throws java.io.IOException {
      return newBuilder().mergeFrom(input).buildParsed();
    }
    public static com.randioo.market_server.protocol.Buyer.BuyerRequest parseFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return newBuilder().mergeFrom(input, extensionRegistry)
               .buildParsed();
    }
    
    public static Builder newBuilder() { return Builder.create(); }
    public Builder newBuilderForType() { return newBuilder(); }
    public static Builder newBuilder(com.randioo.market_server.protocol.Buyer.BuyerRequest prototype) {
      return newBuilder().mergeFrom(prototype);
    }
    public Builder toBuilder() { return newBuilder(this); }
    
    public static final class Builder extends
        com.google.protobuf.GeneratedMessage.Builder<Builder> {
      private com.randioo.market_server.protocol.Buyer.BuyerRequest result;
      
      // Construct using com.randioo.market_server.protocol.Buyer.BuyerRequest.newBuilder()
      private Builder() {}
      
      private static Builder create() {
        Builder builder = new Builder();
        builder.result = new com.randioo.market_server.protocol.Buyer.BuyerRequest();
        return builder;
      }
      
      protected com.randioo.market_server.protocol.Buyer.BuyerRequest internalGetResult() {
        return result;
      }
      
      public Builder clear() {
        if (result == null) {
          throw new IllegalStateException(
            "Cannot call clear() after build().");
        }
        result = new com.randioo.market_server.protocol.Buyer.BuyerRequest();
        return this;
      }
      
      public Builder clone() {
        return create().mergeFrom(result);
      }
      
      public com.google.protobuf.Descriptors.Descriptor
          getDescriptorForType() {
        return com.randioo.market_server.protocol.Buyer.BuyerRequest.getDescriptor();
      }
      
      public com.randioo.market_server.protocol.Buyer.BuyerRequest getDefaultInstanceForType() {
        return com.randioo.market_server.protocol.Buyer.BuyerRequest.getDefaultInstance();
      }
      
      public boolean isInitialized() {
        return result.isInitialized();
      }
      public com.randioo.market_server.protocol.Buyer.BuyerRequest build() {
        if (result != null && !isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return buildPartial();
      }
      
      private com.randioo.market_server.protocol.Buyer.BuyerRequest buildParsed()
          throws com.google.protobuf.InvalidProtocolBufferException {
        if (!isInitialized()) {
          throw newUninitializedMessageException(
            result).asInvalidProtocolBufferException();
        }
        return buildPartial();
      }
      
      public com.randioo.market_server.protocol.Buyer.BuyerRequest buildPartial() {
        if (result == null) {
          throw new IllegalStateException(
            "build() has already been called on this Builder.");
        }
        com.randioo.market_server.protocol.Buyer.BuyerRequest returnMe = result;
        result = null;
        return returnMe;
      }
      
      public Builder mergeFrom(com.google.protobuf.Message other) {
        if (other instanceof com.randioo.market_server.protocol.Buyer.BuyerRequest) {
          return mergeFrom((com.randioo.market_server.protocol.Buyer.BuyerRequest)other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }
      
      public Builder mergeFrom(com.randioo.market_server.protocol.Buyer.BuyerRequest other) {
        if (other == com.randioo.market_server.protocol.Buyer.BuyerRequest.getDefaultInstance()) return this;
        if (other.hasBuyerData()) {
          mergeBuyerData(other.getBuyerData());
        }
        this.mergeUnknownFields(other.getUnknownFields());
        return this;
      }
      
      public Builder mergeFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws java.io.IOException {
        com.google.protobuf.UnknownFieldSet.Builder unknownFields =
          com.google.protobuf.UnknownFieldSet.newBuilder(
            this.getUnknownFields());
        while (true) {
          int tag = input.readTag();
          switch (tag) {
            case 0:
              this.setUnknownFields(unknownFields.build());
              return this;
            default: {
              if (!parseUnknownField(input, unknownFields,
                                     extensionRegistry, tag)) {
                this.setUnknownFields(unknownFields.build());
                return this;
              }
              break;
            }
            case 10: {
              com.randioo.market_server.protocol.Entity.BuyerData.Builder subBuilder = com.randioo.market_server.protocol.Entity.BuyerData.newBuilder();
              if (hasBuyerData()) {
                subBuilder.mergeFrom(getBuyerData());
              }
              input.readMessage(subBuilder, extensionRegistry);
              setBuyerData(subBuilder.buildPartial());
              break;
            }
          }
        }
      }
      
      
      // optional .com.randioo.market_server.protocol.BuyerData buyerData = 1;
      public boolean hasBuyerData() {
        return result.hasBuyerData();
      }
      public com.randioo.market_server.protocol.Entity.BuyerData getBuyerData() {
        return result.getBuyerData();
      }
      public Builder setBuyerData(com.randioo.market_server.protocol.Entity.BuyerData value) {
        if (value == null) {
          throw new NullPointerException();
        }
        result.hasBuyerData = true;
        result.buyerData_ = value;
        return this;
      }
      public Builder setBuyerData(com.randioo.market_server.protocol.Entity.BuyerData.Builder builderForValue) {
        result.hasBuyerData = true;
        result.buyerData_ = builderForValue.build();
        return this;
      }
      public Builder mergeBuyerData(com.randioo.market_server.protocol.Entity.BuyerData value) {
        if (result.hasBuyerData() &&
            result.buyerData_ != com.randioo.market_server.protocol.Entity.BuyerData.getDefaultInstance()) {
          result.buyerData_ =
            com.randioo.market_server.protocol.Entity.BuyerData.newBuilder(result.buyerData_).mergeFrom(value).buildPartial();
        } else {
          result.buyerData_ = value;
        }
        result.hasBuyerData = true;
        return this;
      }
      public Builder clearBuyerData() {
        result.hasBuyerData = false;
        result.buyerData_ = com.randioo.market_server.protocol.Entity.BuyerData.getDefaultInstance();
        return this;
      }
      
      // @@protoc_insertion_point(builder_scope:com.randioo.market_server.protocol.BuyerRequest)
    }
    
    static {
      defaultInstance = new BuyerRequest(true);
      com.randioo.market_server.protocol.Buyer.internalForceInit();
      defaultInstance.initFields();
    }
    
    // @@protoc_insertion_point(class_scope:com.randioo.market_server.protocol.BuyerRequest)
  }
  
  public static final class BuyerResponse extends
      com.google.protobuf.GeneratedMessage {
    // Use BuyerResponse.newBuilder() to construct.
    private BuyerResponse() {
      initFields();
    }
    private BuyerResponse(boolean noInit) {}
    
    private static final BuyerResponse defaultInstance;
    public static BuyerResponse getDefaultInstance() {
      return defaultInstance;
    }
    
    public BuyerResponse getDefaultInstanceForType() {
      return defaultInstance;
    }
    
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return com.randioo.market_server.protocol.Buyer.internal_static_com_randioo_market_server_protocol_BuyerResponse_descriptor;
    }
    
    protected com.google.protobuf.GeneratedMessage.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return com.randioo.market_server.protocol.Buyer.internal_static_com_randioo_market_server_protocol_BuyerResponse_fieldAccessorTable;
    }
    
    // optional int32 errorCode = 1 [default = 1];
    public static final int ERRORCODE_FIELD_NUMBER = 1;
    private boolean hasErrorCode;
    private int errorCode_ = 1;
    public boolean hasErrorCode() { return hasErrorCode; }
    public int getErrorCode() { return errorCode_; }
    
    // repeated .com.randioo.market_server.protocol.TradingData tradingData = 2;
    public static final int TRADINGDATA_FIELD_NUMBER = 2;
    private java.util.List<com.randioo.market_server.protocol.Entity.TradingData> tradingData_ =
      java.util.Collections.emptyList();
    public java.util.List<com.randioo.market_server.protocol.Entity.TradingData> getTradingDataList() {
      return tradingData_;
    }
    public int getTradingDataCount() { return tradingData_.size(); }
    public com.randioo.market_server.protocol.Entity.TradingData getTradingData(int index) {
      return tradingData_.get(index);
    }
    
    private void initFields() {
    }
    public final boolean isInitialized() {
      return true;
    }
    
    public void writeTo(com.google.protobuf.CodedOutputStream output)
                        throws java.io.IOException {
      getSerializedSize();
      if (hasErrorCode()) {
        output.writeInt32(1, getErrorCode());
      }
      for (com.randioo.market_server.protocol.Entity.TradingData element : getTradingDataList()) {
        output.writeMessage(2, element);
      }
      getUnknownFields().writeTo(output);
    }
    
    private int memoizedSerializedSize = -1;
    public int getSerializedSize() {
      int size = memoizedSerializedSize;
      if (size != -1) return size;
    
      size = 0;
      if (hasErrorCode()) {
        size += com.google.protobuf.CodedOutputStream
          .computeInt32Size(1, getErrorCode());
      }
      for (com.randioo.market_server.protocol.Entity.TradingData element : getTradingDataList()) {
        size += com.google.protobuf.CodedOutputStream
          .computeMessageSize(2, element);
      }
      size += getUnknownFields().getSerializedSize();
      memoizedSerializedSize = size;
      return size;
    }
    
    public static com.randioo.market_server.protocol.Buyer.BuyerResponse parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data).buildParsed();
    }
    public static com.randioo.market_server.protocol.Buyer.BuyerResponse parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data, extensionRegistry)
               .buildParsed();
    }
    public static com.randioo.market_server.protocol.Buyer.BuyerResponse parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data).buildParsed();
    }
    public static com.randioo.market_server.protocol.Buyer.BuyerResponse parseFrom(
        byte[] data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return newBuilder().mergeFrom(data, extensionRegistry)
               .buildParsed();
    }
    public static com.randioo.market_server.protocol.Buyer.BuyerResponse parseFrom(java.io.InputStream input)
        throws java.io.IOException {
      return newBuilder().mergeFrom(input).buildParsed();
    }
    public static com.randioo.market_server.protocol.Buyer.BuyerResponse parseFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return newBuilder().mergeFrom(input, extensionRegistry)
               .buildParsed();
    }
    public static com.randioo.market_server.protocol.Buyer.BuyerResponse parseDelimitedFrom(java.io.InputStream input)
        throws java.io.IOException {
      Builder builder = newBuilder();
      if (builder.mergeDelimitedFrom(input)) {
        return builder.buildParsed();
      } else {
        return null;
      }
    }
    public static com.randioo.market_server.protocol.Buyer.BuyerResponse parseDelimitedFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      Builder builder = newBuilder();
      if (builder.mergeDelimitedFrom(input, extensionRegistry)) {
        return builder.buildParsed();
      } else {
        return null;
      }
    }
    public static com.randioo.market_server.protocol.Buyer.BuyerResponse parseFrom(
        com.google.protobuf.CodedInputStream input)
        throws java.io.IOException {
      return newBuilder().mergeFrom(input).buildParsed();
    }
    public static com.randioo.market_server.protocol.Buyer.BuyerResponse parseFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return newBuilder().mergeFrom(input, extensionRegistry)
               .buildParsed();
    }
    
    public static Builder newBuilder() { return Builder.create(); }
    public Builder newBuilderForType() { return newBuilder(); }
    public static Builder newBuilder(com.randioo.market_server.protocol.Buyer.BuyerResponse prototype) {
      return newBuilder().mergeFrom(prototype);
    }
    public Builder toBuilder() { return newBuilder(this); }
    
    public static final class Builder extends
        com.google.protobuf.GeneratedMessage.Builder<Builder> {
      private com.randioo.market_server.protocol.Buyer.BuyerResponse result;
      
      // Construct using com.randioo.market_server.protocol.Buyer.BuyerResponse.newBuilder()
      private Builder() {}
      
      private static Builder create() {
        Builder builder = new Builder();
        builder.result = new com.randioo.market_server.protocol.Buyer.BuyerResponse();
        return builder;
      }
      
      protected com.randioo.market_server.protocol.Buyer.BuyerResponse internalGetResult() {
        return result;
      }
      
      public Builder clear() {
        if (result == null) {
          throw new IllegalStateException(
            "Cannot call clear() after build().");
        }
        result = new com.randioo.market_server.protocol.Buyer.BuyerResponse();
        return this;
      }
      
      public Builder clone() {
        return create().mergeFrom(result);
      }
      
      public com.google.protobuf.Descriptors.Descriptor
          getDescriptorForType() {
        return com.randioo.market_server.protocol.Buyer.BuyerResponse.getDescriptor();
      }
      
      public com.randioo.market_server.protocol.Buyer.BuyerResponse getDefaultInstanceForType() {
        return com.randioo.market_server.protocol.Buyer.BuyerResponse.getDefaultInstance();
      }
      
      public boolean isInitialized() {
        return result.isInitialized();
      }
      public com.randioo.market_server.protocol.Buyer.BuyerResponse build() {
        if (result != null && !isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return buildPartial();
      }
      
      private com.randioo.market_server.protocol.Buyer.BuyerResponse buildParsed()
          throws com.google.protobuf.InvalidProtocolBufferException {
        if (!isInitialized()) {
          throw newUninitializedMessageException(
            result).asInvalidProtocolBufferException();
        }
        return buildPartial();
      }
      
      public com.randioo.market_server.protocol.Buyer.BuyerResponse buildPartial() {
        if (result == null) {
          throw new IllegalStateException(
            "build() has already been called on this Builder.");
        }
        if (result.tradingData_ != java.util.Collections.EMPTY_LIST) {
          result.tradingData_ =
            java.util.Collections.unmodifiableList(result.tradingData_);
        }
        com.randioo.market_server.protocol.Buyer.BuyerResponse returnMe = result;
        result = null;
        return returnMe;
      }
      
      public Builder mergeFrom(com.google.protobuf.Message other) {
        if (other instanceof com.randioo.market_server.protocol.Buyer.BuyerResponse) {
          return mergeFrom((com.randioo.market_server.protocol.Buyer.BuyerResponse)other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }
      
      public Builder mergeFrom(com.randioo.market_server.protocol.Buyer.BuyerResponse other) {
        if (other == com.randioo.market_server.protocol.Buyer.BuyerResponse.getDefaultInstance()) return this;
        if (other.hasErrorCode()) {
          setErrorCode(other.getErrorCode());
        }
        if (!other.tradingData_.isEmpty()) {
          if (result.tradingData_.isEmpty()) {
            result.tradingData_ = new java.util.ArrayList<com.randioo.market_server.protocol.Entity.TradingData>();
          }
          result.tradingData_.addAll(other.tradingData_);
        }
        this.mergeUnknownFields(other.getUnknownFields());
        return this;
      }
      
      public Builder mergeFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws java.io.IOException {
        com.google.protobuf.UnknownFieldSet.Builder unknownFields =
          com.google.protobuf.UnknownFieldSet.newBuilder(
            this.getUnknownFields());
        while (true) {
          int tag = input.readTag();
          switch (tag) {
            case 0:
              this.setUnknownFields(unknownFields.build());
              return this;
            default: {
              if (!parseUnknownField(input, unknownFields,
                                     extensionRegistry, tag)) {
                this.setUnknownFields(unknownFields.build());
                return this;
              }
              break;
            }
            case 8: {
              setErrorCode(input.readInt32());
              break;
            }
            case 18: {
              com.randioo.market_server.protocol.Entity.TradingData.Builder subBuilder = com.randioo.market_server.protocol.Entity.TradingData.newBuilder();
              input.readMessage(subBuilder, extensionRegistry);
              addTradingData(subBuilder.buildPartial());
              break;
            }
          }
        }
      }
      
      
      // optional int32 errorCode = 1 [default = 1];
      public boolean hasErrorCode() {
        return result.hasErrorCode();
      }
      public int getErrorCode() {
        return result.getErrorCode();
      }
      public Builder setErrorCode(int value) {
        result.hasErrorCode = true;
        result.errorCode_ = value;
        return this;
      }
      public Builder clearErrorCode() {
        result.hasErrorCode = false;
        result.errorCode_ = 1;
        return this;
      }
      
      // repeated .com.randioo.market_server.protocol.TradingData tradingData = 2;
      public java.util.List<com.randioo.market_server.protocol.Entity.TradingData> getTradingDataList() {
        return java.util.Collections.unmodifiableList(result.tradingData_);
      }
      public int getTradingDataCount() {
        return result.getTradingDataCount();
      }
      public com.randioo.market_server.protocol.Entity.TradingData getTradingData(int index) {
        return result.getTradingData(index);
      }
      public Builder setTradingData(int index, com.randioo.market_server.protocol.Entity.TradingData value) {
        if (value == null) {
          throw new NullPointerException();
        }
        result.tradingData_.set(index, value);
        return this;
      }
      public Builder setTradingData(int index, com.randioo.market_server.protocol.Entity.TradingData.Builder builderForValue) {
        result.tradingData_.set(index, builderForValue.build());
        return this;
      }
      public Builder addTradingData(com.randioo.market_server.protocol.Entity.TradingData value) {
        if (value == null) {
          throw new NullPointerException();
        }
        if (result.tradingData_.isEmpty()) {
          result.tradingData_ = new java.util.ArrayList<com.randioo.market_server.protocol.Entity.TradingData>();
        }
        result.tradingData_.add(value);
        return this;
      }
      public Builder addTradingData(com.randioo.market_server.protocol.Entity.TradingData.Builder builderForValue) {
        if (result.tradingData_.isEmpty()) {
          result.tradingData_ = new java.util.ArrayList<com.randioo.market_server.protocol.Entity.TradingData>();
        }
        result.tradingData_.add(builderForValue.build());
        return this;
      }
      public Builder addAllTradingData(
          java.lang.Iterable<? extends com.randioo.market_server.protocol.Entity.TradingData> values) {
        if (result.tradingData_.isEmpty()) {
          result.tradingData_ = new java.util.ArrayList<com.randioo.market_server.protocol.Entity.TradingData>();
        }
        super.addAll(values, result.tradingData_);
        return this;
      }
      public Builder clearTradingData() {
        result.tradingData_ = java.util.Collections.emptyList();
        return this;
      }
      
      // @@protoc_insertion_point(builder_scope:com.randioo.market_server.protocol.BuyerResponse)
    }
    
    static {
      defaultInstance = new BuyerResponse(true);
      com.randioo.market_server.protocol.Buyer.internalForceInit();
      defaultInstance.initFields();
    }
    
    // @@protoc_insertion_point(class_scope:com.randioo.market_server.protocol.BuyerResponse)
  }
  
  private static com.google.protobuf.Descriptors.Descriptor
    internal_static_com_randioo_market_server_protocol_BuyerRequest_descriptor;
  private static
    com.google.protobuf.GeneratedMessage.FieldAccessorTable
      internal_static_com_randioo_market_server_protocol_BuyerRequest_fieldAccessorTable;
  private static com.google.protobuf.Descriptors.Descriptor
    internal_static_com_randioo_market_server_protocol_BuyerResponse_descriptor;
  private static
    com.google.protobuf.GeneratedMessage.FieldAccessorTable
      internal_static_com_randioo_market_server_protocol_BuyerResponse_fieldAccessorTable;
  
  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\013Buyer.proto\022\"com.randioo.market_server" +
      ".protocol\032\014Entity.proto\"P\n\014BuyerRequest\022" +
      "@\n\tbuyerData\030\001 \001(\0132-.com.randioo.market_" +
      "server.protocol.BuyerData\"k\n\rBuyerRespon" +
      "se\022\024\n\terrorCode\030\001 \001(\005:\0011\022D\n\013tradingData\030" +
      "\002 \003(\0132/.com.randioo.market_server.protoc" +
      "ol.TradingData"
    };
    com.google.protobuf.Descriptors.FileDescriptor.InternalDescriptorAssigner assigner =
      new com.google.protobuf.Descriptors.FileDescriptor.InternalDescriptorAssigner() {
        public com.google.protobuf.ExtensionRegistry assignDescriptors(
            com.google.protobuf.Descriptors.FileDescriptor root) {
          descriptor = root;
          internal_static_com_randioo_market_server_protocol_BuyerRequest_descriptor =
            getDescriptor().getMessageTypes().get(0);
          internal_static_com_randioo_market_server_protocol_BuyerRequest_fieldAccessorTable = new
            com.google.protobuf.GeneratedMessage.FieldAccessorTable(
              internal_static_com_randioo_market_server_protocol_BuyerRequest_descriptor,
              new java.lang.String[] { "BuyerData", },
              com.randioo.market_server.protocol.Buyer.BuyerRequest.class,
              com.randioo.market_server.protocol.Buyer.BuyerRequest.Builder.class);
          internal_static_com_randioo_market_server_protocol_BuyerResponse_descriptor =
            getDescriptor().getMessageTypes().get(1);
          internal_static_com_randioo_market_server_protocol_BuyerResponse_fieldAccessorTable = new
            com.google.protobuf.GeneratedMessage.FieldAccessorTable(
              internal_static_com_randioo_market_server_protocol_BuyerResponse_descriptor,
              new java.lang.String[] { "ErrorCode", "TradingData", },
              com.randioo.market_server.protocol.Buyer.BuyerResponse.class,
              com.randioo.market_server.protocol.Buyer.BuyerResponse.Builder.class);
          return null;
        }
      };
    com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
          com.randioo.market_server.protocol.Entity.getDescriptor(),
        }, assigner);
  }
  
  public static void internalForceInit() {}
  
  // @@protoc_insertion_point(outer_class_scope)
}
