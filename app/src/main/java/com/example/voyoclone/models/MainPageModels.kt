data class ApiResponse(
    val data: Data,
    val errors: List<Error>?,
)

data class Data(
    val front: Front?
)
data class Error(
    val message: ErrorMessage
)

data class ErrorMessage(
    val code: String,
    val error: Boolean,
    val message: String,
    val status: Int,
    val tMessage: String
)
data class Front(
    val id: Int,
    val sectionId: Int,
    val url: String,
    val dataPart: Int,
    val nbDataParts: Int,
    val section: Section,
    val data: List<FrontData>
)

data class Section(
    val id: Int,
    val metaKeywords: String,
    val metaDescription: String,
    val title: String,
    val windowTitle: String,
    val gemiusCode: String,
    val rootSectionId: Int,
    val gridSectionId: Int,
    val siteId: Int,
    val parentId: Int,
    val voyoCategoryId: Int
)

data class FrontData(
    val itemTypes: String,
    val dataId: String,
    val pages: Int,
    val payloadHash: String,
    val renderer: String,
    val name: String,
    val payload: List<FrontPayload>
)

open class FrontPayload(val __typename: String, val image: Image?, val title: String, val portraitImage: PortraitImage?)

//data class VideoType(
//    val __typename: String,
//    val id: Int,
//    val objectType: String,
//    val title: String,
//    val caption: String,
//    val date: Long,
//    val drmProtected: Boolean,
//    val preroll: Boolean,
//    val postroll: Boolean,
//    val style: String,
//    val url: String,
//    val isAVOD: Boolean,
//    val vtt: String,
//    val subtype: String,
//    val description: String,
//    val image: Image,
//    val portraitImage: PortraitImage,
//    val videoMeta: VideoMeta,
//    val length: Int,
//    val section: Section
//) : FrontPayload()
//
//data class VoyoCategoryType(
//    val __typename: String,
//    val title: String,
//    val description: String,
//    val url: String,
//    val nbVideos: String,
//    val id: Int,
//    val objectType: String,
//    val year: Int,
//    val imdbRating: Int,
//    val originalTitle: String,
//    val voyoCategoryMeta: VoyoCategoryMeta,
//    val startWith: StartWith,
//    val image: Image,
//    val portraitImage: PortraitImage,
//    val wideImage: Image,
//    val submenuImage: Image,
//    val trailers: List<Trailer>
//) : FrontPayload()
//
//data class LiveStreamType(
//    val __typename: String,
//    val id: Int,
//    val url: String,
//    val image: Image,
//    val portraitImage: PortraitImage,
//    val isNowOn: Boolean,
//    val startAt: Long,
//    val endAt: Long,
//    val title: String,
//    val mediaId: Int,
//    val subtitle: String,
//    val allowAVOD: Any?,
//    val isSpecial: Boolean,
//    val timeEndDescription: String,
//    val timeStartDescription: String,
//    val type: String,
//    val description: String,
//    val dayDescription: String,
//    val percentDone: Int,
//    val category: Any
//) : FrontPayload()

data class FrontPayloadWrapper(
    val payload: FrontPayload
)

data class Image(
    val id: Int?,
    val src: String?
)

data class PortraitImage(
    val id: Int?,
    val src: String?
)

data class VideoMeta(
    val voyokey: String,
    val showAsCategory: Boolean,
    val categoryId: Any?,
    val subtitle: String,
    val year: Int,
    val imdbRating: Double,
    val originalTitle: String,
    val restriction: String,
    val posterImage: Image?,
    val trailer: Trailer?
)

data class VoyoCategoryMeta(
    val voyokey: String,
    val subtitle: String
)

data class StartWith(
    val id: Int
)

data class Trailer(
    val id: Int,
    val title: String,
    val image: Image
)
