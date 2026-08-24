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
        let statusOk = StatusCode.Companion.shared.OK
        #expect(statusOk.isSuccess() == true)

        let methodGet = Method.Companion.shared.GET
        #expect(methodGet.asStr() == "GET")

        let version11 = Version.HTTP_11
        #expect(version11.description == "HTTP_11")
    }
}
