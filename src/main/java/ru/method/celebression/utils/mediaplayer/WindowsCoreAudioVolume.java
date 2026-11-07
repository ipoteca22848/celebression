package ru.method.celebression.utils.mediaplayer;

import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.COM.Unknown;
import com.sun.jna.platform.win32.Guid;
import com.sun.jna.platform.win32.Ole32;
import com.sun.jna.platform.win32.WinNT;
import com.sun.jna.ptr.FloatByReference;
import com.sun.jna.ptr.PointerByReference;
import ru.method.celebression.Celebression;

final class WindowsCoreAudioVolume {
    private static final Guid.CLSID CLSID_MMDeviceEnumerator = new Guid.CLSID("BCDE0395-E52F-467C-8E3D-C4579291692E");
    private static final Guid.IID IID_IMMDeviceEnumerator = new Guid.IID("A95664D2-9614-4FCA-AFAC-3E6D667664EF");
    private static final Guid.IID IID_IAudioEndpointVolume = new Guid.IID("5CDF2C82-841E-4546-9722-0CF74078229A");
    private static final int E_DATAFLOW_RENDER = 0;
    private static final int E_ROLE_MULTIMEDIA = 1;
    private static final int CLSCTX_ALL = 23;
    private static volatile EndpointVolume endpointVolume;

    private WindowsCoreAudioVolume() {
    }

    static boolean isAvailable() {
        return WindowsCoreAudioVolume.ensureEndpointVolume() != null;
    }

    static float readScalar() {
        EndpointVolume endpointVolume = WindowsCoreAudioVolume.ensureEndpointVolume();
        if (endpointVolume == null) {
            return -1.0f;
        }
        FloatByReference floatByReference = new FloatByReference();
        WinNT.HRESULT hRESULT = endpointVolume.GetMasterVolumeLevelScalar(floatByReference);
        if (WindowsCoreAudioVolume.failed(hRESULT)) {
            WindowsCoreAudioVolume.invalidate();
            return -1.0f;
        }
        return WindowsCoreAudioVolume.clamp(floatByReference.getValue());
    }

    static boolean writeScalar(float f) {
        EndpointVolume endpointVolume = WindowsCoreAudioVolume.ensureEndpointVolume();
        if (endpointVolume == null) {
            Celebression.LOGGER.warn("[Volume] CoreAudio endpoint unavailable (CoCreate/Activate failed)");
            return false;
        }
        float f2 = WindowsCoreAudioVolume.clamp(f);
        WinNT.HRESULT hRESULT = endpointVolume.SetMasterVolumeLevelScalar(f2, null);
        if (WindowsCoreAudioVolume.failed(hRESULT)) {
            Celebression.LOGGER.warn("[Volume] SetMasterVolumeLevelScalar failed hr=0x{}", (Object)(hRESULT == null ? "null" : Integer.toHexString(hRESULT.intValue())));
            WindowsCoreAudioVolume.invalidate();
            return false;
        }
        FloatByReference floatByReference = new FloatByReference();
        WinNT.HRESULT hRESULT2 = endpointVolume.GetMasterVolumeLevelScalar(floatByReference);
        if (!WindowsCoreAudioVolume.failed(hRESULT2) && Math.abs(floatByReference.getValue() - f2) > 0.02f) {
            Celebression.LOGGER.warn("[Volume] write didn't take: target={} readback={}", (Object)Float.valueOf(f2), (Object)Float.valueOf(floatByReference.getValue()));
            return false;
        }
        return true;
    }

        private static EndpointVolume ensureEndpointVolume() {
        EndpointVolume endpointVolume = WindowsCoreAudioVolume.endpointVolume;
        if (endpointVolume != null) {
            return endpointVolume;
        }
        Class<WindowsCoreAudioVolume> clazz = WindowsCoreAudioVolume.class;
        synchronized (WindowsCoreAudioVolume.class) {
            if (WindowsCoreAudioVolume.endpointVolume != null) {
                return WindowsCoreAudioVolume.endpointVolume;
            }
            WindowsCoreAudioVolume.endpointVolume = WindowsCoreAudioVolume.createEndpointVolume();
            return WindowsCoreAudioVolume.endpointVolume;
        }
    }

    private static EndpointVolume createEndpointVolume() {
        Ole32.INSTANCE.CoInitializeEx(null, 0);
        PointerByReference pointerByReference = new PointerByReference();
        WinNT.HRESULT hRESULT = Ole32.INSTANCE.CoCreateInstance((Guid.GUID)CLSID_MMDeviceEnumerator, null, 23, (Guid.GUID)IID_IMMDeviceEnumerator, pointerByReference);
        if (WindowsCoreAudioVolume.failed(hRESULT)) {
            return null;
        }
        DeviceEnumerator deviceEnumerator = new DeviceEnumerator(pointerByReference.getValue());
        PointerByReference pointerByReference2 = new PointerByReference();
        hRESULT = deviceEnumerator.GetDefaultAudioEndpoint(0, 1, pointerByReference2);
        deviceEnumerator.Release();
        if (WindowsCoreAudioVolume.failed(hRESULT)) {
            Celebression.LOGGER.warn("[Volume] GetDefaultAudioEndpoint failed hr=0x{}", (Object)(hRESULT == null ? "null" : Integer.toHexString(hRESULT.intValue())));
            return null;
        }
        AudioDevice audioDevice = new AudioDevice(pointerByReference2.getValue());
        PointerByReference pointerByReference3 = new PointerByReference();
        hRESULT = audioDevice.Activate(IID_IAudioEndpointVolume, 23, null, pointerByReference3);
        audioDevice.Release();
        if (WindowsCoreAudioVolume.failed(hRESULT)) {
            Celebression.LOGGER.warn("[Volume] IAudioEndpointVolume Activate failed hr=0x{}", (Object)(hRESULT == null ? "null" : Integer.toHexString(hRESULT.intValue())));
            return null;
        }
        return new EndpointVolume(pointerByReference3.getValue());
    }

        private static void invalidate() {
        Class<WindowsCoreAudioVolume> clazz = WindowsCoreAudioVolume.class;
        synchronized (WindowsCoreAudioVolume.class) {
            EndpointVolume endpointVolume = WindowsCoreAudioVolume.endpointVolume;
            WindowsCoreAudioVolume.endpointVolume = null;
            if (endpointVolume != null) {
                endpointVolume.Release();
            }
            return;
        }
    }

    private static boolean failed(WinNT.HRESULT hRESULT) {
        return hRESULT == null || hRESULT.intValue() < 0;
    }

    private static float clamp(float f) {
        return Math.max(0.0f, Math.min(1.0f, f));
    }

    static final class EndpointVolume
    extends Unknown {
        EndpointVolume(Pointer pointer) {
            super(pointer);
        }

        WinNT.HRESULT SetMasterVolumeLevelScalar(float f, Guid.GUID gUID) {
            return (WinNT.HRESULT)this._invokeNativeObject(7, new Object[]{Float.valueOf(f), gUID}, WinNT.HRESULT.class);
        }

        WinNT.HRESULT GetMasterVolumeLevelScalar(FloatByReference floatByReference) {
            return (WinNT.HRESULT)this._invokeNativeObject(9, new Object[]{floatByReference}, WinNT.HRESULT.class);
        }
    }

    static final class DeviceEnumerator
    extends Unknown {
        DeviceEnumerator(Pointer pointer) {
            super(pointer);
        }

        WinNT.HRESULT GetDefaultAudioEndpoint(int n, int n2, PointerByReference pointerByReference) {
            return (WinNT.HRESULT)this._invokeNativeObject(4, new Object[]{n, n2, pointerByReference}, WinNT.HRESULT.class);
        }
    }

    static final class AudioDevice
    extends Unknown {
        AudioDevice(Pointer pointer) {
            super(pointer);
        }

        WinNT.HRESULT Activate(Guid.IID iID, int n, Pointer pointer, PointerByReference pointerByReference) {
            return (WinNT.HRESULT)this._invokeNativeObject(3, new Object[]{iID, n, pointer, pointerByReference}, WinNT.HRESULT.class);
        }
    }
}

