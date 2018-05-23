#pragma once
#include <vector>

constexpr double DEFAULT_SAMPLINGRATE = 44100;
constexpr double DEFAULT_AUDIOVOLUME = 100;
constexpr size_t DEFAULT_BUFFERSIZE = 128;


class Sound
{
private:
	std::vector<double> buffer;
	const double samplingFrequency	{ DEFAULT_SAMPLINGRATE };
	const double audioVolume		{ DEFAULT_AUDIOVOLUME };
	double lastPos{ 0 };
	const size_t bufferSize{ DEFAULT_BUFFERSIZE };
public:
	Sound();
	Sound(double samplingFrequency, double audioVolume, size_t bufferSize);

	void addFrequency(double volume, double frequency);
	const std::vector<double>& getBuffer() const
	{
		return buffer;
	}
	void resetBuffer();


	~Sound() = default;
};

