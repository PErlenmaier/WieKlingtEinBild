#pragma once
#include <fstream>
#include <string>
#include <vector>

typedef struct
{
	uint16_t bfType;
	uint32_t bfFileSize;
	uint16_t bfRes1;
	uint16_t bfRes2;
	uint32_t bfStartAdress;
} BitmapFileHeader;

typedef struct
{
	uint32_t biSize;
	uint32_t biWidth;
	uint32_t biHeight;
	uint16_t biColorPlanes;
	uint16_t biBitsPerPixel;
	uint32_t biCompression;
	uint32_t biImageSize;
	uint32_t biHorizontalResolution;
	uint32_t biVerticalResolution;
	uint32_t biColorsPerColorPalette;
	uint32_t biImportantColorsUsed;
} BitmapInfoHeader;

typedef struct
{
	uint8_t r;
	uint8_t g;
	uint8_t b;
} Pixel;

class Image
{
private:
	BitmapFileHeader bitmapFileHeader;
	BitmapInfoHeader bitmapInfoHeader;
	std::vector<std::vector<Pixel>> bitmapData;
	void readHeader();
	void readImageData();
	std::ifstream file;
public:
	Image() = delete;
	Image(Image&& other) = default;
	Image(std::string fileName);
	Image& operator=(const Image& other) = delete;
	Image& operator=(Image&& other) = delete;

	const Pixel& getPixel(int y, int x) const
	{
		return bitmapData[y][x];
	}

	uint32_t getHeight() const
	{
		return bitmapInfoHeader.biHeight;
	}

	uint32_t getWidth() const
	{
		return bitmapInfoHeader.biWidth;
	}

	~Image();
};

