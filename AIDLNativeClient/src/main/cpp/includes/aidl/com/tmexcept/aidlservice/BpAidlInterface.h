#pragma once

#include "aidl/com/tmexcept/aidlservice/AidlInterface.h"

#include <android/binder_ibinder.h>

namespace aidl {
namespace com {
namespace tmexcept {
namespace aidlservice {
class BpAidlInterface : public ::ndk::BpCInterface<IAidlInterface> {
public:
  BpAidlInterface(const ::ndk::SpAIBinder& binder);
  virtual ~BpAidlInterface();

  ::ndk::ScopedAStatus add(int32_t in_num1, int32_t in_num2, int32_t* _aidl_return) override;
  ::ndk::ScopedAStatus getNum(int32_t* _aidl_return) override;
  ::ndk::ScopedAStatus minus(int32_t in_num1, int32_t in_num2) override;
  ::ndk::ScopedAStatus minus2(int32_t in_num1, int32_t in_num2, int32_t* _aidl_return) override;
  ::ndk::ScopedAStatus testParam(int32_t in_num1, int32_t in_num2, int32_t in_num3, int32_t* _aidl_return) override;
  ::ndk::ScopedAStatus testParam2(int32_t in_num1, int32_t* _aidl_return) override;
};
}  // namespace aidlservice
}  // namespace tmexcept
}  // namespace com
}  // namespace aidl
