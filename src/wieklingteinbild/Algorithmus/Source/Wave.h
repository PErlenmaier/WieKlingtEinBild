#pragma once
#include <fstream>

#define DEFAULT_BITS_PER_SAMPLE 16
#define DEFAULT_BYTES_PER_SAMPLE (DEFAULT_BITS_PER_SAMPLE/8)

class Wave
{
private:
	void prepareHeader();
	std::ofstream file{"WieKlingtEinBild.wav", std::ios::binary};

	struct
	{
		//little endian
		const uint32_t subChunk1Size{16}; //Keine zusaetzlichen Daten
		const uint16_t audioFormat{1}; //Keine Kompression
		const uint16_t numChannels{1};
		uint32_t sampleRate{44100};
		const uint32_t byteRate{static_cast<uint32_t>(sampleRate * numChannels * DEFAULT_BYTES_PER_SAMPLE)};
		const uint16_t blockAlign{static_cast<uint16_t>(numChannels * DEFAULT_BYTES_PER_SAMPLE)};
		const uint16_t bitsPerSample{DEFAULT_BITS_PER_SAMPLE};
	} waveHeader;

public:
	Wave(std::string fileName, uint32_t sampleRate);
	Wave();

	template <typename T>
	std::ofstream& writeSamples(T data)
	{
		size_t size = sizeof(T);
		while (size--)
		{
			file.put(static_cast<uint8_t>(data & 0xFF));
			data >>= 8;
		}

		return file;
	}

	void finishHeader();
	void closeFile();
	~Wave();
};
