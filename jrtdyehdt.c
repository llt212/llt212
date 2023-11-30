#include <random>
#include <unordered_set>
#include <graphics.h>
#include <cmath>

#define PINK LIGHTRED | 0x6055ff
#define LIGHTPINK LIGHTRED | 0x6655ff
#define DRAW(vecs, color) for(auto& vec : vecs)Draw(vec, color, distribution(engine))

using namespace std;

constexpr float Pi = 3.1416f;
constexpr float Rad = Pi / 180;
constexpr int ScreenWidth = 800;
constexpr int ScreenHeight = 600;
constexpr int OX = ScreenWidth / 2;
constexpr int OY = ScreenHeight / 2;

static default_random_engine engine;

struct Vec2
{
	float X = .0f;
	float Y = .0f;

	Vec2() {}
	Vec2(float x, float y) { X = x; Y = y; }

	int GetX() const { return static_cast<int>(X); }
	int GetY() const { return static_cast<int>(Y); }
	
	float Magnitude() const { return sqrt(X * X + Y * Y); }
	Vec2 operator*(float num) const{ return Vec2(X * num, Y * num); }

	struct VecHash
	{
		size_t operator()(const Vec2& vec) const noexcept
		{
			return std::hash<float>()(vec.X) ^ std::hash<float>()(vec.Y);
		}
	};

	struct VecCompare
	{
		bool operator()(const Vec2& vec1, const Vec2& vec2) const noexcept
		{
			return fabsf(vec1.X - vec2.X) < 1e-2f && fabsf(vec1.Y - vec2.Y) < 1e-2f;
		}
	};
};
using VecSet = unordered_set<Vec2, Vec2::VecHash, Vec2::VecCompare>;


float CalculateX(float t){ return powf(sin(t), 3.0f); }
float CalculateY(float t){ return -(13 * cosf(t) - 5 * cosf(2 * t) - 2 * cosf(3 * t) - cosf(4 * t)) / 16.0f; }


VecSet InitHeart(float startAngle, float endAngle, float radius, size_t count)
{
	VecSet set;
	float rad = startAngle * Rad;
	float step = (endAngle - startAngle) * Rad / count;
	float endRad = endAngle * Rad;

	for (; rad < endRad; rad += step)
        set.insert(Vec2(CalculateX(rad) * radius, CalculateY(rad) * radius));

	return set;
}


VecSet BuildInside(const VecSet& set, size_t frequency, float lambda, float range, float min)
{
	VecSet retSet;
	exponential_distribution<float> distribution(lambda);

	for (size_t i = 0; i < frequency; i++)
	{
		for (auto& vec : set)
		{
			float pX = distribution(engine);
			float scalarX = (pX < 1.0 ? 1.0f - pX : 1.0f) * range + min;

			float pY = distribution(engine);
			float scalarY = (pY < 1.0 ? 1.0f - pY : 1.0f) * range + min;

			retSet.insert(Vec2(vec.X * scalarX, vec.Y * scalarY));
		}
	}

	return retSet;
}


VecSet BuildOutside(const VecSet& set, size_t frequency, float max)
{
	VecSet retSet;
	uniform_real_distribution<float> distribution(1.0f, max);

	for (size_t i = 0; i < frequency; i++)
	{
		for (auto& vec : set)
            retSet.insert(Vec2(vec.X * distribution(engine), vec.Y *  distribution(engine)));
	}

	return retSet;
}


VecSet Undulate(const VecSet& set, float radius)
{
	VecSet retSet;
	uniform_real_distribution<float> distribution(-radius, radius);

	for (auto& vec : set)
		retSet.insert(Vec2(vec.X + distribution(engine), vec.Y + distribution(engine)));

	return retSet;
}


VecSet Zoom(const VecSet& set, float factor, float radius, float t, float idx)
{
	VecSet retSet;

	for (auto& vec : set)
        retSet.insert(vec * (1.0f + factor * sin(t * Pi) * powf((radius / vec.Magnitude()), idx)));

	return retSet;
}


void Draw(const Vec2& vec, COLORREF color, int radius)
{
	putpixel(vec.GetX() + OX, vec.GetY() + OY, color);

	if(radius >= 2)
		putpixel(vec.GetX() + OX + 1, vec.GetY() + OY, color);

	if(radius >= 3)
		putpixel(vec.GetX() + OX, vec.GetY() + OY + 1, color);
}
	

int main()
{
	float radius = 160.0f;
	auto border = InitHeart(0, 360, radius, 480);
	auto inside = BuildInside(border, 30, 5.0f, 0.85f, 0.15f);

	initgraph(ScreenWidth, ScreenHeight);
	BeginBatchDraw();

	float t = .0f;
	float tStep = 0.05f;
	uniform_int_distribution<int> distribution(1, 3);
	ExMessage msg{};

	while(!peekmessage(&msg, EX_KEY))
	{
		auto ps1 = Zoom(border, 0.1f, radius, t, 1.3f);
		auto ps2 = Undulate(Zoom(inside, 0.1f, radius, t, 1.3f), 3.0f);
		auto ps3 = Undulate(BuildOutside(border, 10 - static_cast<size_t>(sin(t) * 5), 1.35f - sin(t) * 0.15f), 3.0f);

		DRAW(ps1, LIGHTPINK);
		DRAW(ps2, LIGHTPINK);
		DRAW(ps3, PINK);

		FlushBatchDraw();
		Sleep(40);

		t += tStep;
		if (t > 1.0f)
			t = .0f;

		cleardevice();
	}

	EndBatchDraw();
	closegraph();

	return 0;
}
