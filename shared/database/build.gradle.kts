plugins {
    alias(libs.plugins.okik.shared.kpp)
    alias(libs.plugins.sqldelight)
}

android {
    namespace = "okik.tech.community.admin.shared.storage"
}

//sqldelight {
//    databases {
//        create("CADatabase") {
//            packageName.set("okik.tech.community.admin.shared.storage")
//        }
//    }
//}