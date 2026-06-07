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
    iproute2 \
    dhcpcd \
    gdb \
    strace \
    nano \
    libnl \
    libgpiod \
    vibration-monitor \
    linux-firmware-rpidistro-bcm43455 \
    libtensorflow-lite \
    python3-tensorflow-lite \
"
IMAGE_FEATURES += " \
    ssh-server-openssh \
    debug-tweaks \
"
IMAGE_INSTALL:append = " vibration-monitor"

inherit extrausers

EXTRA_USERS_PARAMS = "usermod -p '\$6\$xyz\$.LA3IZQhaac9mspnV.DIfjpr01DdnIo7C1p/Jb0Zv.4Oth1vnM3NBYGNLpn.NCBQe6P7nipTrkWldQgIYfPiH/' root;"

ROOTFS_POSTPROCESS_COMMAND += "allow_root_ssh_login; setup_wifi; "
allow_root_ssh_login() {
    sed -i 's/^#PermitRootLogin.*/PermitRootLogin yes/' \
        ${IMAGE_ROOTFS}/etc/ssh/sshd_config
    sed -i 's/^#PasswordAuthentication.*/PasswordAuthentication yes/' \
        ${IMAGE_ROOTFS}/etc/ssh/sshd_config
}


setup_wifi() {
    # create wpa_supplicant init script
    cat > ${IMAGE_ROOTFS}/etc/init.d/wpa_supplicant << 'INITEOF'
#!/bin/sh
### BEGIN INIT INFO
# Provides:          wpa_supplicant
# Required-Start:    $network
# Default-Start:     2 3 4 5
# Default-Stop:      0 1 6
### END INIT INFO

case "$1" in
    start)
        wpa_supplicant -B -i wlan0 \
            -c /etc/wpa_supplicant.conf \
            -D nl80211
        sleep 3
        dhcpcd wlan0 &
        ;;
    stop)
        killall wpa_supplicant 2>/dev/null
        dhcpcd -x wlan0 2>/dev/null
        ;;
    *)
        echo "Usage: $0 {start|stop}"
        exit 1
        ;;
esac
exit 0
INITEOF

    chmod +x ${IMAGE_ROOTFS}/etc/init.d/wpa_supplicant

    # enable at boot runlevels 2-5
    ln -sf ../init.d/wpa_supplicant \
        ${IMAGE_ROOTFS}/etc/rc2.d/S08wpa_supplicant
    ln -sf ../init.d/wpa_supplicant \
        ${IMAGE_ROOTFS}/etc/rc3.d/S08wpa_supplicant
    ln -sf ../init.d/wpa_supplicant \
        ${IMAGE_ROOTFS}/etc/rc4.d/S08wpa_supplicant
    ln -sf ../init.d/wpa_supplicant \
        ${IMAGE_ROOTFS}/etc/rc5.d/S08wpa_supplicant
}

APPEND += " isolcpus=3 nohz_full=3 rcu_nocbs=3"

IMAGE_FSTYPES = "ext4 rpi-sdimg"