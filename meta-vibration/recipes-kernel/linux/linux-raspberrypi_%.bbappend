FILESEXTRAPATHS:prepend := "${THISDIR}/files:"
#remove the original kernel's uri
SRC_URI:remove = "git://github.com/raspberrypi/linux.git;name=machine;branch=rpi-6.6.y;protocol=https"
#location of the downloaded kernel in the device 
SRC_URI:prepend = "git:///home/vboxuser/linux-c1432b4bae5b6582f4d32ba381459f33c34d1424;name=machine;protocol=file;nobranch=1 " 
#commit hash of the kernel's local repo
SRCREV_machine = "86c466de8e3bef20a565263076c6dca2c421783a"

KERNEL_VERSION_SANITY_SKIP = "1"
#removed unsupported dtb files in this kernel version
KERNEL_DEVICETREE:remove = "broadcom/bcm2712-rpi-cm5l-cm5io.dtb \
                            broadcom/bcm2712-rpi-cm5l-cm4io.dtb "

#patch and the needed kconfig modifications files
SRC_URI:append = " file://patch-6.6.30-rt30.patch \
                   file://preempt-rt.cfg "