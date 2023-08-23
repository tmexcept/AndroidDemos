#pragma once

#include <android/binder_interface_utils.h>

#include <cstdint>
#include <memory>
#include <optional>
#include <string>
#include <vector>
#ifdef BINDER_STABILITY_SUPPORT
#include <android/binder_stability.h>
#endif  // BINDER_STABILITY_SUPPORT

namespace aidl {
namespace com {
namespace tmexcept {
namespace aidlservice {
class IAidlInterface : public ::ndk::ICInterface {
public:
  static const char* descriptor;
  IAidlInterface();
  virtual ~IAidlInterface();



  static std::shared_ptr<IAidlInterface> fromBinder(const ::ndk::SpAIBinder& binder);
  static binder_status_t writeToParcel(AParcel* parcel, const std::shared_ptr<IAidlInterface>& instance);
  static binder_status_t readFromParcel(const AParcel* parcel, std::shared_ptr<IAidlInterface>* instance);
  static bool setDefaultImpl(std::shared_ptr<IAidlInterface> impl);
  static const std::shared_ptr<IAidlInterface>& getDefaultImpl();
  virtual ::ndk::ScopedAStatus add(int32_t in_num1, int32_t in_num2, int32_t* _aidl_return) = 0;
  virtual ::ndk::ScopedAStatus getNum(int32_t* _aidl_return) = 0;
  virtual ::ndk::ScopedAStatus minus(int32_t in_num1, int32_t in_num2) = 0;
  virtual ::ndk::ScopedAStatus minus2(int32_t in_num1, int32_t in_num2, int32_t* _aidl_return) = 0;
  virtual ::ndk::ScopedAStatus testParam(int32_t in_num1, int32_t in_num2, int32_t in_num3, int32_t* _aidl_return) = 0;
  virtual ::ndk::ScopedAStatus testParam2(int32_t in_num1, int32_t* _aidl_return) = 0;
private:
  static std::shared_ptr<IAidlInterface> default_impl;
};
class IAidlInterfaceDefault : public IAidlInterface {
public:
  ::ndk::ScopedAStatus add(int32_t in_num1, int32_t in_num2, int32_t* _aidl_return) override;
  ::ndk::ScopedAStatus getNum(int32_t* _aidl_return) override;
  ::ndk::ScopedAStatus minus(int32_t in_num1, int32_t in_num2) override;
  ::ndk::ScopedAStatus minus2(int32_t in_num1, int32_t in_num2, int32_t* _aidl_return) override;
  ::ndk::ScopedAStatus testParam(int32_t in_num1, int32_t in_num2, int32_t in_num3, int32_t* _aidl_return) override;
  ::ndk::ScopedAStatus testParam2(int32_t in_num1, int32_t* _aidl_return) override;
  ::ndk::SpAIBinder asBinder() override;
  bool isRemote() override;
};
}  // namespace aidlservice
}  // namespace tmexcept
}  // namespace com
}  // namespace aidl
