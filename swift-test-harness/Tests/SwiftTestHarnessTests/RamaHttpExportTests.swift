import Testing
import RamaHttp

@Suite("RamaHttp Swift Export Smoke Tests")
struct RamaHttpExportTests {
    @Test("RamaHttp swift module imports cleanly")
    func swiftModuleLoads() {
        #expect(Bool(true))
    }

    @Test("RamaHttp exported types instantiate cleanly")
    func exportedTypesInstantiate() {
        let hostHeader = ExportedKotlinPackages.io.github.kotlinmania.ramahttp.types.HeaderName.Companion.shared.HOST
        #expect(hostHeader.asStr() == "host")

        let methodGet = ExportedKotlinPackages.io.github.kotlinmania.ramahttp.types.Method.Companion.shared.GET
        #expect(methodGet.asStr() == "GET")

        let version11 = ExportedKotlinPackages.io.github.kotlinmania.ramahttp.types.Version.HTTP_11
        #expect(version11.description == "HTTP_11")
    }
}
