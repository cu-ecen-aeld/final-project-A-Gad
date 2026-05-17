
FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

SRC_URI:append = " file://wpa_supplicant.conf"

PACKAGECONFIG:append = " libnl"

RDEPENDS:${PN} += "libnl libnl-genl libnl-route"

do_install:append() {
    install -d ${D}${sysconfdir}/wpa_supplicant/
    install -m 0600 ${WORKDIR}/wpa_supplicant.conf \
        ${D}${sysconfdir}/wpa_supplicant/wpa_supplicant-wlan0.conf
}