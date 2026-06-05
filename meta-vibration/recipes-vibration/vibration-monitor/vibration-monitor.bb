SUMMARY = "Motor vibration acquisition system"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"
SRC_URI = "gitsm://github.com/A-Gad/data_acquistion_test.git;protocol=https;branch=main"
SRCREV = "fda2fdb47d9ca9ff45fd0af014bd6303637b4ca1"

S = "${WORKDIR}/git"

inherit pkgconfig cmake

DEPENDS = ""

