# final-project-A-Gad

# Motor Vibration Analysis System

[Project Overview](../../wiki/Project-Overview)


# Vibration Monitor — Build Instructions

## Dependencies
Clone these alongside this repo:

git clone https://git.yoctoproject.org/poky -b scarthgap
git clone https://github.com/agherzan/meta-raspberrypi -b scarthgap
git clone https://github.com/openembedded/meta-openembedded -b scarthgap

## Setup
source poky/oe-init-build-env build
bitbake vibration-image
