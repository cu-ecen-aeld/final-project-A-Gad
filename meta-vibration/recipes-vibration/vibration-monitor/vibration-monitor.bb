SUMMARY = "Motor vibration acquisition system"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=0"
SRC_URI = "git://github.com/A-Gad/data_acquistion_test.git;protocol=https;branch=main"
SRCREV = "bb4ad3a95819494ba17f7deae4f5a385e8b6c2b2"

S = "${WORKDIR}/git"

inherit pkgconfig

DEPENDS = ""

do_compile() {
    oe_runmake
}

do_install() {
    install -d ${D}${bindir}
    install -m 0755 ${B}/vibration-monitor ${D}${bindir}
}