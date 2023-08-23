#pragma once

#include "aidl/com/tmexcept/aidlservice/AidlInterface.h"

#include <android/binder_ibinder.h>

namespace aidl {
namespace com {
namespace tmexcept {
namespace aidlservice {
class BnAidlInterface : public ::ndk::BnCInterface<IAidlInterface> {
public:
  BnAidlInterface();
  virtual ~BnAidlInterface();
protected:
  ::ndk::SpAIBinder createBinder() override;
private:
};
}  // namespace aidlservice
}  // namespace tmexcept
}  // namespace com
}  // namespace aidl
