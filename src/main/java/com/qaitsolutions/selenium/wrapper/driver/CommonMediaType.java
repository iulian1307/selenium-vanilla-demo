package com.qaitsolutions.selenium.wrapper.driver;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public enum CommonMediaType {

    AAC_audio("audio/aac"),
    AbiWord_document("application/x-abiword"),
    Archive_document("application/x-freearc"),
    AVIF_image("image/avif"),
    Audio_Video_Interleave("video/x-msvideo"),
    Amazon_Kindle_eBook_format("application/vnd.amazon.ebook"),
    Any_kind_of_binary_data("application/octet-stream"),
    Windows_OS2_Bitmap_Graphics("image/bmp"),
    BZip_archive("application/x-bzip"),
    BZip2_archive("application/x-bzip2"),
    CD_audio("application/x-cdf"),
    C_Shell_script("application/x-csh"),
    Cascading_Style_Sheets("text/css"),
    Comma_separated_values("text/csv"),
    Microsoft_Word("application/msword"),
    Microsoft_Word_OPEN_XML("application/vnd.openxmlformats-officedocument.wordprocessingml.document"),
    MS_Embedded_OpenType_fonts("application/vnd.ms-fontobject"),
    Electronic_publication("application/epub+zip"),
    GZip_Compressed_Archive("application/gzip"),
    Graphics_Interchange_Format("image/gif"),
    HyperText_Markup_Language("text/html"),
    Icon_format("image/vnd.microsoft.icon"),
    iCalendar_format("text/calendar"),
    Java_Archive("application/java-archive"),
    JPEG_images("image/jpeg"),
    JavaScript("text/javascript"),
    JSON_format("application/json"),
    JSON_LD_format("application/ld+json"),
    Musical_Instrument_Digital_Interface("audio/midi, audio/x-midi"),
    JavaScript_module("text/javascript"),
    MP3_audio("audio/mpeg"),
    MP4_video("video/mp4"),
    MPEG_Video("video/mpeg"),
    Apple_Installer_Package("application/vnd.apple.installer+xml"),
    OpenDocument_presentation_document("application/vnd.oasis.opendocument.presentation"),
    OpenDocument_spreadsheet_document("application/vnd.oasis.opendocument.spreadsheet"),
    OpenDocument_text_document("application/vnd.oasis.opendocument.text"),
    OGG_audio("audio/ogg"),
    OGG_video("video/ogg"),
    OGG("application/ogg"),
    Opus_audio("audio/opus"),
    OpenType_font("font/otf"),
    Portable_Network_Graphics("image/png"),
    Adobe_Portable_Document_Format("application/pdf"),
    Hypertext_Preprocessor("application/x-httpd-php"),
    Microsoft_PowerPoint("application/vnd.ms-powerpoint"),
    Microsoft_PowerPoint_OPEN_XML("application/vnd.openxmlformats-officedocument.presentationml.presentation"),
    RAR_archive("application/vnd.rar"),
    Rich_Text_Format("application/rtf"),
    Bourne_shell_script("application/x-sh"),
    Scalable_Vector_Graphics("image/svg+xml"),
    Tape_Archive("application/x-tar"),
    Tagged_Image_File_Format("image/tiff"),
    MPEG_transport_stream("video/mp2t"),
    TrueType_Font("font/ttf"),
    Text("text/plain"),
    Microsoft_Visio("application/vnd.visio"),
    Waveform_Audio_Format("audio/wav"),
    WEBM_audio("audio/webm"),
    WEBM_video("video/webm"),
    WEBP_image("image/webp"),
    Web_Open_Font_Format("font/woff"),
    Web_Open_Font_Format2("font/woff2"),
    XHTML("application/xhtml+xml"),
    Microsoft_Excel("application/vnd.ms-excel"),
    Microsoft_Excel_OPEN_XML("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"),
    XML("application/xml"),
    XUL("application/vnd.mozilla.xul+xml"),
    ZIP_archive("application/zip"),
    GPP_audio_video_container("video/3gpp; audio/3gpp"),
    GPP2_audio_video_container("video/3gpp2; audio/3gpp2"),
    zip_archive("application/x-7z-compressed");

    private final String mime;

    public static String getAllWithSeparator(String separator) {
        var values = List.of(values());
        var allMimes = new StringBuilder();

        for (CommonMediaType value : values)
            allMimes.append(value.getMime()).append(separator);

        return allMimes.toString();
    }
}
