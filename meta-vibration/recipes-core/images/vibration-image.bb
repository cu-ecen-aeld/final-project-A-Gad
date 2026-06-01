SUMMARY = "Motor Vibration Analysis System Image"
DESCRIPTION = "Minimal RT image for vibration monitoring on Pi 5"

require recipes-core/images/core-image-minimal.bb

FORTRAN:forcevariable = ",fortran"

IMAGE_INSTALL += " \
    kernel-modules \
    packagegroup-core-boot \
    packagegroup-core-buildessential \
    rt-tests \
    spitools \
    i2c-tools \
    libstdc++ \
    libgcc \
    python3 \
    python3-numpy \
    python3-pip \
    sqlite3 \
    python3-sqlite3 \
    wpa-supplicant \
    linux-firmware-rpidistro-bcm43455 \
    iproute2 \
    dhcpcd \
    gdb \
    strace \
    nano \
"
IMAGE_INSTALL += " \
    gdb \
    strace \
    nano \
"
IMAGE_INSTALL += " \
    linux-firmware-rpidistro-bcm43455 \
    iproute2 \
    dhcpcd \
    libnl \
    libgpiod \
"

IMAGE_FEATURES += " \
    ssh-server-openssh \
    debug-tweaks \
"

inherit extrausers

EXTRA_USERS_PARAMS = "usermod -p '$6$xyz$.LA3IZQhaac9mspnV.DIfjpr01DdnIo7C1p/Jb0Zv.4Oth1vnM3NBYGNLpn.NCBQe6P7nipTrkWldQgIYfPiH/' root;"

ROOTFS_POSTPROCESS_COMMAND += "allow_root_ssh_login;"
allow_root_ssh_login() {
    sed -i 's/^#PermitRootLogin.*/PermitRootLogin yes/' \
        ${IMAGE_ROOTFS}/etc/ssh/sshd_config
    sed -i 's/^#PasswordAuthentication.*/PasswordAuthentication yes/' \
        ${IMAGE_ROOTFS}/etc/ssh/sshd_config
}

ROOTFS_POSTPROCESS_COMMAND += "enable_wifi;"
enable_wifi() {
    mkdir -p ${IMAGE_ROOTFS}/etc/systemd/system/multi-user.target.wants/
    ln -sf /lib/systemd/system/wpa_supplicant@.service \
        ${IMAGE_ROOTFS}/etc/systemd/system/multi-user.target.wants/wpa_supplicant@wlan0.service
}

APPEND += " isolcpus=3 nohz_full=3 rcu_nocbs=3"

IMAGE_FSTYPES = "ext4 rpi-sdimg"